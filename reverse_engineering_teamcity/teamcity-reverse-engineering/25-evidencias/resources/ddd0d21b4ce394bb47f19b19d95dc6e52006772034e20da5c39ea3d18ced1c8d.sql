delete
  from action_history
  where action = 25;

delete
  from comments
  where not exists ( select *
                     from action_history
                     where comment_id = comments.id );
