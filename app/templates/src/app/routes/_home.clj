(ns <%= baseName %>.routes.home
  (:use compojure.core)
  (:require [noir.io :as io]))

(defroutes home-routes
  (GET "/" [] (io/get-resource "/index.html")))
