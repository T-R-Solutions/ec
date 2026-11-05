#RENAME_TABLE{inspection_fixes; inspection_fixes_old};

create table inspection_fixes
(
  hash #LONG_INT_TYPE not null,
  hint varchar(255)
);

insert
  into inspection_fixes
  select hash, hint
    from inspection_fixes_old
    where hash is not null;

drop table inspection_fixes_old;

create index inspection_fixes_hash_index
  on inspection_fixes (hash);

