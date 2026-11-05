create table build_labels (build_id bigint, vcs_root_id bigint, vcs_root_version bigint, label varchar(80));
create index build_labels_build on build_revisions (build_id);