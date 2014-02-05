(ns <%= baseName %>.models.schema
  (:use [lobos.core :only (defcommand migrate)])
  (:require [noir.io :as io]
            [lobos.migration :as lm]))

(def db-store "site.db")

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str (io/resource-path) db-store)
              :user "sa"
              :password ""
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})

(defcommand pending-migrations []
  (lm/pending-migrations db-spec sname))

(defn actualized?
  "checks if there are no pending migrations"
  []
  (empty? (pending-migrations)))

(def actualize migrate)

