
create table build_project
(
  build_id #LONG_INT_TYPE not null,
  project_level #INT_TYPE not null,
  project_int_id #STRING_TYPE{80} not null
);

#CREATE_PK{build_project; build_project_pk; build_id,project_level};

create index build_project_project_idx
  on build_project (project_int_id);

