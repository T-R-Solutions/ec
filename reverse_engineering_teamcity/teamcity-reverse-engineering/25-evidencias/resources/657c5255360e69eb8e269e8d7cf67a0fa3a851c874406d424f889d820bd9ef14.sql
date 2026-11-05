drop table cloud_instance_state;

create table cloud_instance_state
(
  project_id #STRING_TYPE{80} not null,
  profile_id #STRING_TYPE{30} not null,
  image_id #STRING_TYPE{80} not null,
  instance_id #STRING_TYPE{80} not null,
  name #STRING_TYPE{80} not null,
  last_update #TIMESTAMP_TYPE not null,
  status #STRING_TYPE{30} not null,
  start_time #TIMESTAMP_TYPE not null,
  network_identity #STRING_TYPE{80} null,
  agent_id #INT_TYPE null,
  is_expired #BOOLEAN_TYPE null
);

#CREATE_PK{cloud_instance_state;cloud_instance_state_pk; project_id, profile_id, image_id, instance_id};