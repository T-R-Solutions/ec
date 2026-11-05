create table build_problem_muted
(
  build_state_id #LONG_INT_TYPE not null,
  problem_identity #STRING_TYPE{60} not null,
  mute_id #INT_TYPE,
  primary key (build_state_id, problem_identity)
)
