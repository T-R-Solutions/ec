create table cloud_started_instance
(
  profile_id varchar(30) not null,
  image_id varchar(80) not null,
  instance_id varchar(80) not null,
  unique (profile_id, image_id, instance_id)
)
