;; Copyright (C) 2013 Selventa, Inc.
;;
;; This file is part of the BEL Framework REST API.
;;
;; This program is free software; you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; The BEL Framework REST API is distributed in the hope that it will be
;; useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
;; General Public License for more details.
;;
;; You should have received a copy of the GNU Lesser General Public License
;; along with the BEL Framework REST API. If not, see
;; <http://www.gnu.org/licenses/>.
;;
;; Additional Terms under LGPL v3:
;;
;; This license does not authorize you and you are prohibited from using the
;; name, trademarks, service marks, logos or similar indicia of Selventa, Inc.,
;; or, in the discretion of other licensors or authors of the program, the
;; name, trademarks, service marks, logos or similar indicia of such authors or
;; licensors, in any marketing or advertising materials relating to your
;; distribution of the program or any covered product. This restriction does
;; not waive or limit your obligation to keep intact all copyright notices set
;; forth in the program as delivered to you.
;;
;; If you distribute the program in whole or in part, or any modified version
;; of the program, and you assume contractual liability to the recipient with
;; respect to the program or modified version, then you will indemnify the
;; authors and licensors of the program for any liabilities that these
;; contractual assumptions directly impose on those licensors and authors.

(ns dataloader.core
  (:require [monger.core :as mg])
  (:require [monger.collection :as coll])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId])
  (:gen-class))

(use '[clojure.tools.namespace.repl :only (refresh)]
     '[clojure.xml]
     '[clojure.java.io]
     '[clojure.string :only (split)])

(defn out [x]
  "Prints and flushes x to stdout."
  (. (. System out) print x)
  (flush))

(defn outln [x]
  "Printlns x to stdout."
  (. (. System out) println x))

(defn download-url [url fname]
  "Download a URL and save it to fname."
  (->> (slurp url) (spit fname)))

(defn namespace-urls [idx]
  "Get BEL namespace (.belns) URLs from the resource index idx."
  (def ret [])
  (doseq [x (xml-seq (parse idx))
    :when (= :idx:namespace (:tag x))]
    (doseq [y (:attrs x)]
      (def ret (conj ret (y 1)))))
  (lazy-seq ret))

(defn anno-urls [idx]
  "Get BEL annotation (.belanno) URLs from the resource index idx."
  (def ret[])
  (doseq [x (xml-seq (parse idx))
    :when (= :idx:annotationdefinition (:tag x))]
    (doseq [y (:attrs x)]
      (def ret (conj ret (y 1)))))
  (lazy-seq ret))

(defn parse-annotation [annofile]
  "Get a map representation of a BEL annotation (.belanno)."
  (def annomap {})
  (def values[])
  (with-open [rdr (reader annofile)]
    (def val-block false)
    ; for keyword, type, description, and usage
    (def ad-block false)

  ;   (doseq [line (line-seq rdr)]
  ;     (if (true? val-block)
  ;       (do
  ;         (def tokens (split line #"\|"))
  ;         (def value [(tokens 0) (tokens 1)])
  ;         (def values (conj values value))))
  ;     (if (true? ns-block)
  ;       (do
  ;         (def tokens (split line #"="))
  ;         (if (= "Keyword" (tokens 0))
  ;           (def nsmap (merge nsmap {:keyword (tokens 1)})))
  ;         (if (= "NameString" (tokens 0))
  ;           (def nsmap (merge nsmap {:name (tokens 1)})))
  ;         (if (= "DescriptionString" (tokens 0))
  ;           (def nsmap (merge nsmap {:description (tokens 1)})))))
  ;     (if (re-matches #"\[.*\]" line)
  ;       (do
  ;         (def ns-block false)
  ;         (def val-block false)
  ;         (if (= line "[Values]")
  ;           (def val-block true))
  ;         (if (= line "[Namespace]")
  ;           (def ns-block true))))))
  ; (merge nsmap {:values values}))

(defn parse-namespace [nsfile]
  "Get a map representation of a BEL namespace (.belns)."
  (def nsmap {})
  (def values [])
  (with-open [rdr (reader nsfile)]
    (def val-block false)
    ; for keyword, name, and description
    (def ns-block false)
    (doseq [line (line-seq rdr)]
      (if (true? val-block)
        (do
          (def tokens (split line #"\|"))
          (def value [(tokens 0) (tokens 1)])
          (def values (conj values value))))
      (if (true? ns-block)
        (do
          (def tokens (split line #"="))
          (if (= "Keyword" (tokens 0))
            (def nsmap (merge nsmap {:keyword (tokens 1)})))
          (if (= "NameString" (tokens 0))
            (def nsmap (merge nsmap {:name (tokens 1)})))
          (if (= "DescriptionString" (tokens 0))
            (def nsmap (merge nsmap {:description (tokens 1)})))))
      (if (re-matches #"\[.*\]" line)
        (do
          (def ns-block false)
          (def val-block false)
          (if (= line "[Values]")
            (def val-block true))
          (if (= line "[Namespace]")
            (def ns-block true))))))
  (merge nsmap {:values values}))

(defn -main
  "Loads BEL ancillary namespace data into MongoDB."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ; sanity check environment
  ; define host, db, residx from environment
  (def host (System/getenv "_ENV_MONGO_HOST"))
  (def db (System/getenv "_ENV_MONGO_DB"))
  (def residx (System/getenv "_ENV_BEL_RESIDX"))

  (if (nil? host)
    (outln "no _ENV_MONGO_HOST is set"))
  (if (nil? db)
    (outln "no _ENV_MONGO_DB is set"))
  (if (nil? residx)
    (outln "no _ENV_BEL_RESIDX is set"))
  (if (nil? (or host db residx))
    (System/exit 1))

  (out "Connecting to MongoDB... ")
  ; connect to MongoDB and set the database
  (mg/connect! { :host host })
  (mg/set-db! (mg/get-db db))
  (outln "okay")
  (out "Dropping collections... ")
  ; (coll/drop "namespaces")
  ; (coll/drop "nsvalues")
  (outln "okay")
  ; (doseq [x (namespace-urls residx)]
  ;   (out (str x "... "))
  ;   (download-url x "temp.belns")
  ;   (def nsmap (parse-namespace "temp.belns"))
  ;   (def nsmeta-id (ObjectId.))
  ;   (def nsmeta-doc (assoc (dissoc nsmap :values) :_id nsmeta-id))
  ;   (coll/insert "namespaces" nsmeta-doc)
  ;   (doseq [value (nsmap :values)]
  ;     (def nsval-id (ObjectId.))
  ;     (def nsval-meta {:_id nsval-id :nsmeta-id nsmeta-id})
  ;     (def nsval-norm (clojure.string/lower-case (value 0)))
  ;     (def nsval-data {:enc (value 1) :val (value 0) :norm nsval-norm})
  ;     (coll/insert "nsvalues" (conj nsval-meta nsval-data)))
  ;   (outln "okay"))
  (doseq [x (anno-urls residx)]
    (out (str x "... "))
    (download-url x "temp.belanno")
    (def annomap (parse-annotation "temp.belanno"))
    (outln "okay"))
  (mg/disconnect!))
