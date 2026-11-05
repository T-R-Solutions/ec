create table data_storage_dict
(
  metric_id       #LONG_INT_TYPE not null,
  value_type_key  #STRING_TYPE{200},
  primary key (metric_id),
  unique (value_type_key)
)