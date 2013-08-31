;; Copyright 2013 OpenBEL Consortium;;
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;;   http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.


(ns dataloader.core
  (:require [monger.core :as mg])
  (:require [monger.collection :as coll])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId])
  (:gen-class))

(use '[clojure.tools.namespace.repl :only (refresh)]
     '[clojure.xml]
     '[clojure.java.io]
     '[clojure.string :only (split lower-case)])

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
    ; for name
    (def cit-block false)
    (doseq [line (line-seq rdr)]
      (if (true? val-block)
        (do
          (def tokens (split line #"\|"))
          (def value (tokens 0))
          (def values (conj values value))))
      (if (true? ad-block)
        (do
          (def tokens (split line #"="))
          (if (= "Keyword" (tokens 0))
            (def annomap (merge annomap {:keyword (tokens 1)})))
          (if (= "TypeString" (tokens 0))
            (def annomap (merge annomap {:type (tokens 1)})))
          (if (= "DescriptionString" (tokens 0))
            (def annomap (merge annomap {:description (tokens 1)})))
          (if (= "UsageString" (tokens 0))
            (def annomap (merge annomap {:usage (tokens 1)})))))
      (if (true? cit-block)
        (do
          (def tokens (split line #"="))
          (if (= "NameString" (tokens 0))
            (def annomap (merge annomap {:name (tokens 1)})))))
      (if (re-matches #"\[.*\]" line)
        (do
          (def ad-block false)
          (def val-block false)
          (def cit-block false)
          (if (= line "[Values]")
            (def val-block true))
          (if (= line "[Citation]")
            (def cit-block true))
          (if (= line "[AnnotationDefinition]")
            (def ad-block true))))))
  (merge annomap {:values values}))

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
  (out "Removing objects from collections... ")
  (coll/remove "namespaces")
  (coll/remove "nsvalues")
  (coll/remove "annotations")
  (coll/remove "annovalues")
  (outln "okay")
  (outln "\nNAMESPACES\n--")
  (doseq [x (namespace-urls residx)]
    (out (str x "... "))
    (download-url x "temp.belns")
    (def nsmap (parse-namespace "temp.belns"))
    (def nsmeta-id (ObjectId.))
    (def nsmeta-kwnorm (clojure.string/lower-case (get nsmap :keyword)))
    (def nsmeta-doc (assoc (dissoc nsmap :values)
      :_id nsmeta-id
      :url x
      :norm nsmeta-kwnorm))
    (coll/insert "namespaces" nsmeta-doc)
    (doseq [value (nsmap :values)]
      (def nsval-id (ObjectId.))
      (def nsval-meta {:_id nsval-id :nsmeta-id nsmeta-id})
      (def nsval-norm (clojure.string/lower-case (value 0)))
      (def nsval-data {:enc (value 1) :val (value 0) :norm nsval-norm})
      (coll/insert "nsvalues" (conj nsval-meta nsval-data)))
    (outln "okay"))
  (outln "\nANNOTATIONS\n--")
  (doseq [x (anno-urls residx)]
    (out (str x "... "))
    (download-url x "temp.belanno")
    (def annomap (parse-annotation "temp.belanno"))
    (def annometa-id (ObjectId.))
    (def annometa-kwnorm (clojure.string/lower-case (get annomap :keyword)))
    (def annometa-doc (assoc (dissoc annomap :values)
      :_id annometa-id
      :url x
      :norm annometa-kwnorm))
    (coll/insert "annotations" annometa-doc)
    (doseq [value (annomap :values)]
      (def annoval-id (ObjectId.))
      (def annoval-meta {:_id annoval-id :annometa-id annometa-id})
      (def annoval-norm (lower-case value))
      (def annoval-data {:val value :norm annoval-norm})
      (coll/insert "annovalues" (conj annoval-meta annoval-data)))
    (outln "okay"))
  (mg/disconnect!))
