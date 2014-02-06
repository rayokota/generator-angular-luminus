(ns <%= baseName %>.models.db
  (:use korma.core
        clojure.core.incubator
        [korma.db :only (defdb)])
  (:require [clojure.set :as st]
            [<%= baseName %>.models.schema :as schema]))

(defn date-formatter [dt]
  (.format (new java.text.SimpleDateFormat "yyyy-MM-dd") dt))

(defn generic-transform
  "Transform function for queries. Arguments are a function to apply (f),
   the entity to be transformed, and the fields on which to apply the transformation."
  [f ent fields]
  (cond (seqable? ent)
    (let [update-fn f
          ent ent
          fields (vec (st/intersection (set (keys ent)) (set fields)))]
      (reduce #(update-in %1 [%2] update-fn) ent fields))
    :else ent))

(defdb db schema/db-spec)

<% _.each(entities, function (entity) { %>
(defentity <%= pluralize(entity.name) %>
  (transform
    #(generic-transform date-formatter % [<% _.each(entity.attrs, function (attr) { if (attr.attrType == 'Date') { %> :<%= attr.attrName %><% }}); %>])))

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

