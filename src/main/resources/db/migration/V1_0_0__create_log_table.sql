create table sourced_events
(
    id uuid not null,
    stream varchar not null,
    type varchar not null,
    event jsonb not null,
    version int not null,
    created_at timestamp not null
);

create unique index sourced_events_id_uindex
    on sourced_events (id);

create unique index sourced_events_stream_version_uindex
    on sourced_events (stream, version);

alter table sourced_events
    add constraint sourced_events_pk
        primary key (id);

