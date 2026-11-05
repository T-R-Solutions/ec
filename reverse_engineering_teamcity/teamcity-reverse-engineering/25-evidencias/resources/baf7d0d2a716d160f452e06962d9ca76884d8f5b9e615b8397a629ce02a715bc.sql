delete
  from action_history
  where action = 0 -- build_comment.getdbid()
   and object_id not in ( select build_id
                            from history
                          union
                          select build_id
                            from running )
