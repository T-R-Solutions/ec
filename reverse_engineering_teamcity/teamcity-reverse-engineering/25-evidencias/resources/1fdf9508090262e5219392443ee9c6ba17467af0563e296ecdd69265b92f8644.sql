create index history_agname_bfintimesrv_i
  on history (agent_name, build_finish_time_server);

create index light_history_agname_bftime_i
  on light_history (agent_name, build_finish_time_server);
