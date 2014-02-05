(ns lobos.migrations
  (:refer-clojure 
   :exclude [alter drop bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema config)))

<% _.each(entities, function (entity) { %>
(defmigration add-<%= pluralize(entity.name) %>-table
  (up [] (create
          (table :<%= pluralize(entity.name) %>
                 (integer :id :primary-key)
                 <% _.each(entity.attrs, function (attr) { %>
                 (<%= attr.attrImplType %> :<%= attr.attrName %><% if (attr.attrType === 'String' || attr.attrType === 'Enum') { if (attr.maxLength) { %> <%= attr.maxLength %><% } else { %> 255<% }} %>)<% }); %>
          )))
  (down [] (drop (table :<%= pluralize(entity.name) %>))))
<% }); %>
