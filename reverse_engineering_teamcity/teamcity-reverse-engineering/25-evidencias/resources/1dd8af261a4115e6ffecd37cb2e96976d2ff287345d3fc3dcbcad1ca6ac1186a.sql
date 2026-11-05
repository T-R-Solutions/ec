drop table cloud_started_instance;

create table cloud_started_instance
(
  profile_id varchar(30) not null,
  cloud_code varchar(6) not null,
  image_id varchar(80) not null,
  instance_id varchar(80) not null,
  running #BOOLEAN_TYPE not null,
  last_update #TIMESTAMP_TYPE not null,
  primary key (profile_id, cloud_code, image_id, instance_id)
);

create table cloud_image
(
  profile_id varchar(30) not null,
  cloud_code varchar(6) not null,
  image_id varchar(80) not null,
  contains_agent #BOOLEAN_TYPE not null,
  last_update #TIMESTAMP_TYPE not null,
  primary key (profile_id, cloud_code, image_id)
);

