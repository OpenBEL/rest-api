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

(defproject dataloader "experimental"
  :description "Loads BEL ancillary data into MongoDB."
  :license "LGPLv3"
  :url "https://github.com/OpenBEL/rest-api"
	:dependencies [[org.clojure/clojure "1.5.1"]
                 [com.novemberain/monger "1.5.0"]
                 [org.clojure/tools.namespace "0.2.3"]]
  :main dataloader.core)
