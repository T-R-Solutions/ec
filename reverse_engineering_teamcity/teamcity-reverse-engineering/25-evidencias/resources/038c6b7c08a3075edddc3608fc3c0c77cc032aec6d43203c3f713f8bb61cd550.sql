create table agent_type_param_2
(
  cloud_code  varchar(6) not null,
  profile_id  varchar(30) not null,
  type_id     varchar(60) not null,
  param_name  varchar(160) not null,
  param_value varchar(2000)
);

insert
  into agent_type_param_2
  select *
    from agent_type_param;

drop table agent_type_param;

create table agent_type_param
(
  cloud_code  varchar(6) not null,
  profile_id  varchar(30) not null,
  type_id     varchar(60) not null,
  param_kind  char(1) not null, -- 'D' - defined (before resolving), 'R' - resolved
  param_name  varchar(160) not null,
  param_value varchar(2000),
  primary key (cloud_code, profile_id, type_id, param_kind, param_name)
);

insert
  into agent_type_param (cloud_code, profile_id, type_id, param_kind, param_name, param_value)
  select cloud_code, profile_id, type_id, 'R', param_name, param_value
    from agent_type_param_2;

drop table agent_type_param_2;
