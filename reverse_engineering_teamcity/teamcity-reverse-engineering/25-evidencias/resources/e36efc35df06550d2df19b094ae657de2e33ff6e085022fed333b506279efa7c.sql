create table agent_pool
(
  agent_pool_id    #INT_TYPE not null,
  agent_pool_name  #STRING_TYPE{30} not null
);

#CREATE_PK{agent_pool; agent_pool_pk; agent_pool_id};
#CREATE_UI{agent_pool; agent_pool_ak; agent_pool_name};

insert
  into agent_pool (agent_pool_id, agent_pool_name)
  values (0, 'Default');

commit;


create table agent_type_old
(
  agent_type_id    #INT_TYPE not null,
  cloud_code       #STRING_TYPE{6} not null,
  profile_id       #STRING_TYPE{30} not null,
  image_id         #STRING_TYPE{60} not null
);

insert
   into agent_type_old (agent_type_id, cloud_code, profile_id, image_id)
   select agent_type_id, cloud_code, profile_id, image_id
     from agent_type;

commit;


drop table agent_type;


create table agent_type
(
  agent_type_id    #INT_TYPE not null,
  agent_pool_id    #INT_TYPE not null,
  cloud_code       #STRING_TYPE{6} not null,
  profile_id       #STRING_TYPE{30} not null,
  image_id         #STRING_TYPE{60} not null,
  policy           #INT_TYPE not null
);

#CREATE_PK{agent_type; agent_type_pk; agent_type_id};
#CREATE_UI{agent_type; agent_type_ak; cloud_code, profile_id, image_id};
#CREATE_IE{agent_type; agent_type_pool_i; agent_pool_id};

insert
  into agent_type (agent_type_id, agent_pool_id, cloud_code, profile_id, image_id, policy)
  select T.agent_type_id, 0, T.cloud_code, T.profile_id, T.image_id,
         #NVL{X.policy; 1}
    from agent_type_old T
         left join ( select agent_type_id, max(P.policy) as policy
                     from agent A,
                          run_configuration_policy P
                     where A.id = P.agent_id
                     group by agent_type_id ) X
              on T.agent_type_id = X.agent_type_id;

commit;


create table agent_type_bt_access
(
  agent_type_id    #INT_TYPE not null,
  build_type_id    #STRING_TYPE{20} not null
);

#CREATE_PK{agent_type_bt_access; agent_type_bt_access_pk; agent_type_id, build_type_id};
#CREATE_IE{agent_type_bt_access; agent_type_bt_access_bt_i; build_type_id};

insert
  into agent_type_bt_access (agent_type_id, build_type_id)
  select A.agent_type_id, B.build_type_id
    from agent A, agent_build_type_access B
    where A.id = B.agent_id
      and A.agent_type_id is not null;

commit;


drop table agent_build_type_access;
drop table run_configuration_policy;
drop table agent_type_old;

