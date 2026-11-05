create table server_health_items
(
  id           #LONG_INT_TYPE not null,
  report_id    #STRING_TYPE{80} not null,
  category_id  #STRING_TYPE{80} not null,
  item_id      #STRING_TYPE{255} not null,
  visible      #BOOLEAN_TYPE not null,
  primary key (id)
);

create index server_health_items_ie on server_health_items (report_id, category_id);