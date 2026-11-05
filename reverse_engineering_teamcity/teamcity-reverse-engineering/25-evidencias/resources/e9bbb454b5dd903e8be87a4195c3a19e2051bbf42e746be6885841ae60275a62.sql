create table build_parameters
(
  build_state_id #LONG_INT_TYPE,
  param_name #STRING_TYPE{2000},
  param_value #LONG_STRING_TYPE{16000}
);

create index build_parameters_state_id
  on build_parameters (build_state_id);

