delete
  from responsibilities
  where responsible_user_id not in (select id from users);
