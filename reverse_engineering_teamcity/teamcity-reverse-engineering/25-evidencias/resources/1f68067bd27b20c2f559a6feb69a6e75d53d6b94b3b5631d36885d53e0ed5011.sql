delete from agent_sources_version where current_version is null;
delete from build_revisions where vcs_revision is null;
delete from vcs_history where version is null;
