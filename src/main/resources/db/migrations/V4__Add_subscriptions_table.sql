create table user_subsriptions (
        channel_id int8 not null,
        subscriber_id int8 not null,
        primary key (channel_id, subscriber_id)
)