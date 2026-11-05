create table cleanup_history
(
  proc_id      #LONG_INT_TYPE not null,
  start_time   #LONG_INT_TYPE not null,
  finish_time  #LONG_INT_TYPE,
  reason       #STRING_TYPE{20},
  primary key (proc_id)
);

