delete
  from action_history
  where action = 18;

delete
  from comments
  where not exists ( select *
                     from action_history
                     where comment_id = comments.id );
