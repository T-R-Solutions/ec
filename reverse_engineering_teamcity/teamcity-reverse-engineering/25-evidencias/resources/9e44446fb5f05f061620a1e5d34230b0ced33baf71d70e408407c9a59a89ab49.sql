#RENAME_TABLE{clean_checkout_enforcement; clean_checkout_enforcement_old};

create table clean_checkout_enforcement
(
  build_type_id #STRING_TYPE{80} not null,
  agent_id #INT_TYPE not null,
  current_build_id #LONG_INT_TYPE not null,
  request_time #TIMESTAMP_TYPE not null,
  primary key (build_type_id, agent_id)
);

insert into clean_checkout_enforcement
select build_type_id, agent_id, max(current_build_id), max(request_time)
from clean_checkout_enforcement_old
group by build_type_id, agent_id;

drop table clean_checkout_enforcement_old;