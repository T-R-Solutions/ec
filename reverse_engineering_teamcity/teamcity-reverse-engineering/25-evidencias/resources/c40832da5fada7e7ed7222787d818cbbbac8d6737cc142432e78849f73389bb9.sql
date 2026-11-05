create table build_state_tag
(
  build_state_id #LONG_INT_TYPE not null,
  tag #UNI_STRING_TYPE{255} not null
);

#CREATE_PK{build_state_tag; build_state_tag_pk; build_state_id, tag};
#CREATE_IE{build_state_tag; build_state_tag_ie1; tag, build_state_id};


create table build_state_private_tag
(
  build_state_id #LONG_INT_TYPE not null,
  owner #LONG_INT_TYPE not null,
  tag #UNI_STRING_TYPE{255} not null
);

#CREATE_PK{build_state_private_tag; build_state_private_tag_pk; build_state_id, owner, tag};
#CREATE_IE{build_state_private_tag; build_state_private_tag_ie1; owner, build_state_id};


insert into build_state_tag (build_state_id, tag)
select distinct h.build_state_id, t.tag
from history h join tc_build_tags t
  on h.build_id = t.build_id
where h.build_state_id is not null
  and t.tag is not null;

commit;

drop table tc_build_tags;

