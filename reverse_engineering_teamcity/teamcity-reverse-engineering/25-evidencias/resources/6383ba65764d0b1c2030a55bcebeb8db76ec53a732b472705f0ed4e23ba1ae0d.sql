update action_history
set
  action = action + 69, -- 99 and 100 instead of 30 and 31 respectively
  object_id = (select #NUMBER_VCHAR{agent_type_id} from agent where id = #VCHAR_NUMBER{action_history.object_id})
where action in (30, 31);