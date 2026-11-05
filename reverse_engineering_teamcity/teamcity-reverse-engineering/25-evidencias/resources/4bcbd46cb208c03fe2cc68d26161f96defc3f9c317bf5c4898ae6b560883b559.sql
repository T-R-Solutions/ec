drop table agent_sources_version;

create table agent_sources_version
(
       build_type_id #STRING_TYPE{20} not null,
       vcs_root_id integer not null,
       agent_id integer not null,
       directory_name #STRING_TYPE{16} not null,
       current_version #STRING_TYPE{80},
       primary key(build_type_id, directory_name, vcs_root_id, agent_id)
);

