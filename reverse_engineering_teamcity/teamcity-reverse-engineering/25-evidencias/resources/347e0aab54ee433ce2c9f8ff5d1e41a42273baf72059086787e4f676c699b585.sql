delete from test_metadata;

drop table test_metadata_dict;

create table test_metadata_dict
(
  key_id #INT_TYPE not null,
  name_digest #STRING_TYPE{32} not null,
  name #UNI_STRING_TYPE{512} not null
);

#CREATE_PK{test_metadata_dict; test_metadata_dict_pk; key_id};
#CREATE_UI{test_metadata_dict; test_metadata_dict_ak; name_digest};
