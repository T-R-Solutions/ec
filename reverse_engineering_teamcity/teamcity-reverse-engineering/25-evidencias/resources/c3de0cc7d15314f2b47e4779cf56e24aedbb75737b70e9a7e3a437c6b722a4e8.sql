delete
  from downloaded_artifacts
  where source_build_id not in ( select build_id
                                 from history )
