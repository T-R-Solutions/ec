create table projects
(
  project_id #STRING_TYPE{20} not null,
  build_type_id #STRING_TYPE{20} not null,
  project_name #STRING_TYPE{1024},
  build_type_name #STRING_TYPE{1024},
  primary key (project_id, build_type_id)
)