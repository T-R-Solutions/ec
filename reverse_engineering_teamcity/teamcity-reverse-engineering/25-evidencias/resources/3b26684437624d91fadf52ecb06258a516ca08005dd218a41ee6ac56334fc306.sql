create table clean_checkout_enforcement
(
  id #LONG_INT_TYPE not null,
  build_type_id #STRING_TYPE{80} not null,
  agent_id #INT_TYPE not null,
  current_build_id #LONG_INT_TYPE not null,
  request_time #TIMESTAMP_TYPE not null,
  primary key (id)
);
