create table hidden_notifications
(
  type #STRING_TYPE{80} not null,
  id #STRING_TYPE{256} not null
);

create index notifications_type_ie on hidden_notifications (type);
