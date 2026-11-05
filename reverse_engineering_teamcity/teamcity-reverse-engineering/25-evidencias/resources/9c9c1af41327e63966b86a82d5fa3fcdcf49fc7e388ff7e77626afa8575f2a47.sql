create table light_history
(
  build_id bigint,
  agent_name varchar(80),
  build_type_id varchar(20),
  project_id varchar(80),
  start_time bigint,
  finish_time bigint,
  status integer,
  status_text varchar(256),
  pin boolean,
  is_personal boolean,
  is_canceled boolean,
  build_number varchar(32),
  requestor varchar(64),
  primary key (build_id)
);

create index start_time_index_light on light_history (start_time);
create index build_type_id_index_light on light_history (build_type_id);
create index pinned_index_light on light_history (pin);
create index status_index_light on light_history (status);

