(ns <%= baseName %>.routes.<%= pluralize(name) %>
  (:use compojure.core)
  (:require [noir.io :as io]
            [noir.response :as resp]
            [<%= baseName %>.models.db :as db]))

(defroutes <%= name %>-routes
  (context "/<%= baseName %>/<%= pluralize(name) %>" []
    (GET "/" [] (resp/json (db/get-<%= pluralize(name) %>)))
    (GET "/:id" [id] (resp/json (db/get-<%= name %> (Integer/valueOf id))))
    (POST "/"
      [<% _.each(attrs, function (attr) { %><%= attr.attrName %> <% }); %>]
      (db/save-<%= name %> <% _.each(attrs, function (attr) { %><%= attr.attrName %> <% }); %>))
    (PUT "/:id"
      [id <% _.each(attrs, function (attr) { %><%= attr.attrName %> <% }); %>]
      (resp/json (db/update-<%= name %> (Integer/valueOf id) <% _.each(attrs, function (attr) { %><%= attr.attrName %> <% }); %>)))
    (DELETE "/:id" [id] 
      (db/delete-<%= name %> (Integer/valueOf id)) 
      {:status 204})))
