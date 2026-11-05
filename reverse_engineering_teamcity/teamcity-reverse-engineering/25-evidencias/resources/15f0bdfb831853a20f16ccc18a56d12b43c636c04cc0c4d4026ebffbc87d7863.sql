insert into agent_pool_project (agent_pool_id, project_int_id)
select 0, '_Root'
from single_row
where not exists ( select 1
                   from agent_pool_project
                   where agent_pool_id = 0
                     and project_int_id = '_Root' )
