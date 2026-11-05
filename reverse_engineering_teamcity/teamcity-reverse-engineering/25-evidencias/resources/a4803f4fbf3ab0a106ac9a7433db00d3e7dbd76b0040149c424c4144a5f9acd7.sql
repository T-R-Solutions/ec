insert
  into build_problems (build_state_id, problem_identity, problem_type, problem_description)
  select distinct history.build_state_id, 'TC_CONVERTED', 'TC_CONVERTED', 'Build problem added by TeamCity converter'
    from history
    where history.status > 2
      and not exists (select *
                      from build_problems
                      where build_problems.build_state_id=history.build_state_id)