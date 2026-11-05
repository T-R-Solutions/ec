ALTER TABLE build_queue DROP COLUMN when_queued;

ALTER TABLE running DROP COLUMN queued_time;
ALTER TABLE running DROP COLUMN remove_from_queue_time;

ALTER TABLE history DROP COLUMN queued_time;
ALTER TABLE history DROP COLUMN remove_from_queue_time;