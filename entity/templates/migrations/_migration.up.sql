CREATE TABLE <%= pluralize(name) %> (
    <% _.each(attrs, function (attr) { %>
    <%= attr.attrName %> <%= attr.attrImplType %><% if (attr.attrType == 'Enum' || attr.attrType == 'String') { if (attr.maxLength) { %>(<%= attr.maxLength %>)<% } else { %>(255)<% }} %>, <%}); %>
    id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
);
