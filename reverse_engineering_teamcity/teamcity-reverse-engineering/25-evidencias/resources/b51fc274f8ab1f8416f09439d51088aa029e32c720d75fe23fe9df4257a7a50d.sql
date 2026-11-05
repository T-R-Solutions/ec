create table build_problems_attribute
(
  build_state_id #LONG_INT_TYPE not null,
  problem_identity #STRING_TYPE{60} not null,
  attr_name #STRING_TYPE{60} not null,
  attr_value #STRING_TYPE{2000} not null,
  primary key (build_state_id, problem_identity, attr_name)
)
