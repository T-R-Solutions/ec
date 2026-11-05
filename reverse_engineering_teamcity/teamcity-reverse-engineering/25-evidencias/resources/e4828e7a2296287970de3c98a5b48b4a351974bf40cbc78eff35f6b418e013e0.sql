create index inspection_results_buildhash_i
    on inspection_results (build_id, hash);

create unique index inspection_diff_ak
    on inspection_diff (build_id, hash);