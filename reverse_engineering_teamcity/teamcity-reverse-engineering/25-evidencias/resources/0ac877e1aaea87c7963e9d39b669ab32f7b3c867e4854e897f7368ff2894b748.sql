delete
  from mute_info_bt
  where mute_id not in (select mute_id from mute_info);

delete
  from mute_info_test
  where mute_id not in (select mute_id from mute_info);

delete
  from mute_test_in_proj
  where mute_id not in (select mute_id from mute_info);

delete
  from mute_test_in_bt
  where mute_id not in (select mute_id from mute_info);

delete
  from test_muted
  where mute_id not in (select mute_id from mute_info);
