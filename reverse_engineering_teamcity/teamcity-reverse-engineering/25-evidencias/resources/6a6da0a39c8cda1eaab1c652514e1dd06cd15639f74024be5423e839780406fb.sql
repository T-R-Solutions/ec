update build_data_storage
  set metric_id =   4260528966270845747   -- hash.calc("CodeCoverageL")
  where metric_id = 9018439616396414058   -- hash.calc("CodeCoverage");

update build_data_storage
  set metric_id =   -6826520637656720981  -- hash.calc("InspectionStatsW") + "
  where metric_id = 7099310522317309347   -- hash.calc("InspectionStats");

