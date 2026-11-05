create table personal_build_relative_path
(
  build_id #LONG_INT_TYPE,
  original_path_hash #LONG_INT_TYPE,
  relative_path #LONG_STRING_TYPE{16000},
  unique (build_id, original_path_hash)
)