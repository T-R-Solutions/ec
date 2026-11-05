create table build_dependency
(
  build_state_id #LONG_INT_TYPE,
  depends_on #LONG_INT_TYPE,
  unique(build_state_id, depends_on)
)
