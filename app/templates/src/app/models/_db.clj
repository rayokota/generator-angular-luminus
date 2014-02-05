(ns <%= baseName %>.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [<%= baseName %>.models.schema :as schema]))

(defdb db schema/db-spec)

<% _.each(entities, function (entity) { %>
(defentity <%= pluralize(entity.name) %>)

(defn get-<%= pluralize(entity.name) %> []
  (select <%= pluralize(entity.name) %>))

(defn get-<%= entity.name %> [id]
  (first (select <%= pluralize(entity.name) %> (where {:id id}))))

(defn save-<%= entity.name %>
  [<% _.each(entity.attrs, function (attr) { %><%= attr.attrName %> <% }); %>]
  (insert <%= pluralize(entity.name) %>
    (values {<% _.each(entity.attrs, function (attr) { %>
             :<%= attr.attrName %> <%= attr.attrName %><% }); %>
            })))

(defn update-<%= entity.name %>
  [id <% _.each(entity.attrs, function (attr) { %><%= attr.attrName %> <% }); %>]
  (update <%= pluralize(entity.name) %>
    (set-fields {<% _.each(entity.attrs, function (attr) { %>
             :<%= attr.attrName %> <%= attr.attrName %><% }); %>
            })
    (where {:id id})))

(defn delete-<%= entity.name %> [id]
  (delete <%= pluralize(entity.name) %> (where {:id id})))
<% }); %>

