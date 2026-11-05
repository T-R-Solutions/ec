delete
  from action_history
  where action = 16;

delete
  from comments
  where not exists ( select *
                     from action_history
                     where comment_id = comments.id );
