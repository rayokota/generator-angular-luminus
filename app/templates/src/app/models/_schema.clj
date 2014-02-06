(ns <%= baseName %>.models.schema
  (:require [noir.io :as io]))

(def db-path "/tmp/mydb")

(def db-spec {:classname "org.apache.derby.jdbc.EmbeddedDriver"
              :subprotocol "derby"
              :subname db-path
              :create true
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
