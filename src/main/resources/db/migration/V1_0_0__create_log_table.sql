create table event_store
(
    id uuid not null,
    stream varchar not null,
    type varchar not null,
    event jsonb not null,
    version int not null,
    created_at timestamp not null
);

create unique index event_store_id_uindex
    on event_store (id);

create unique index event_store_stream_version_uindex
    on event_store (stream, version);

alter table event_store
    add constraint event_store_pk
        primary key (id);

