create table test_failure_rate
(
  build_type_id #STRING_TYPE{20} not null,
  agent_id integer not null,
  test_name_id #LONG_INT_TYPE not null,
  success_count integer,
  failure_count integer,
  primary key (build_type_id, agent_id, test_name_id)
)