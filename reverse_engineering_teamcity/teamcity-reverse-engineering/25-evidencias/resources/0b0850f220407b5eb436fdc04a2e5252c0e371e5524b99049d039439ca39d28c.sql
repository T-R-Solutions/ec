create table agent_pool_project
(
  agent_pool_id #INT_TYPE not null,
  project_id  #STRING_TYPE{80} not null
);

#CREATE_PK{agent_pool_project; agent_pool_project_pk; agent_pool_id,project_id};

