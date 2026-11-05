create table agent_type_runner_old
(
    agent_type_id #INT_TYPE not null,
    runner #STRING_TYPE{30} not null
);

create table agent_type_vcs_old
(
    agent_type_id #INT_TYPE not null,
    vcs #STRING_TYPE{30} not null
);

insert
  into agent_type_runner_old (agent_type_id, runner)
  select distinct agent_type_id, runner
    from agent_type_runner;

insert
  into agent_type_vcs_old (agent_type_id, vcs)
  select distinct agent_type_id, vcs
    from agent_type_vcs;

commit;


drop table agent_type_runner;
drop table agent_type_vcs;


create table agent_type_runner
(
    agent_type_id #INT_TYPE not null,
    runner #STRING_TYPE{250} not null
);

#CREATE_PK{agent_type_runner; agent_type_runner_pk; agent_type_id, runner};

create table agent_type_vcs
(
    agent_type_id #INT_TYPE not null,
    vcs #STRING_TYPE{250} not null
);

#CREATE_PK{agent_type_vcs; agent_type_vcs_pk; agent_type_id, vcs};


insert
  into agent_type_runner (agent_type_id, runner)
  select agent_type_id, runner
    from agent_type_runner_old;

insert
  into agent_type_vcs (agent_type_id, vcs)
  select agent_type_id, vcs
    from agent_type_vcs_old;

commit;


drop table agent_type_runner_old;
drop table agent_type_vcs_old;

