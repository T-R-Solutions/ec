create table action_history
(
  object_id #LONG_INT_TYPE,
  comment_id #LONG_INT_TYPE,
  action integer
);

create index action_history_object on action_history (object_id);
create index action_history_comment on action_history (comment_id);
create index action_history_action on action_history (action);
