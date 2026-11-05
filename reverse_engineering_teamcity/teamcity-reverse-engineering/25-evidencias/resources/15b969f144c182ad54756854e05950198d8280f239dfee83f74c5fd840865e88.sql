create table build_revisions (build_id bigint, vcs_root_id bigint, vcs_root_version bigint, vcs_revision varchar(80));
create index build_revisions_build on build_revisions (build_id);

