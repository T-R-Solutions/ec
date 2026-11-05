create table agent_type
(
  cloud_code         varchar(6) not null,
  profile_id         varchar(30) not null,
  type_id            varchar(60) not null,
  os_name            varchar(60) not null,
  cpu_rank           #INT_TYPE,
  created_timestamp  #TIMESTAMP_TYPE,
  modified_timestamp #TIMESTAMP_TYPE,
  primary key (cloud_code, profile_id, type_id)
);

create table agent_type_runner
(
  cloud_code  varchar(6) not null,
  profile_id  varchar(30) not null,
  type_id     varchar(60) not null,
  runner      varchar(30) not null,
  unique (cloud_code, profile_id, type_id, runner)
);

create table agent_type_vcs
(
  cloud_code  varchar(6) not null,
  profile_id  varchar(30) not null,
  type_id     varchar(60) not null,
  vcs         varchar(30) not null,
  unique (cloud_code, profile_id, type_id, vcs)
);

create table agent_type_param
(
  cloud_code  varchar(6) not null,
  profile_id  varchar(30) not null,
  type_id     varchar(60) not null,
  param_name  varchar(160) not null,
  param_value varchar(2000),
  primary key (cloud_code, profile_id, type_id, param_name)
);
