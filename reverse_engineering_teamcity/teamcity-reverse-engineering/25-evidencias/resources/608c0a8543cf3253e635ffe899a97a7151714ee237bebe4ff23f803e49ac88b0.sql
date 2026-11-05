update build_state set remove_from_queue_time=queued_time where remove_from_queue_time is null and build_id=id and is_canceled=1;
