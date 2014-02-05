(ns lobos.config
  (:use lobos.connectivity)
  (:require [<%= baseName %>.models.schema :as schema]))

(open-global schema/db-spec)
