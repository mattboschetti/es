create table event_store_outbox
(
    id uuid not null,
    created_at timestamp not null
);

create unique index event_store_outbox_id_uindex
    on event_store_outbox (id);

alter table event_store_outbox
    add constraint event_store_outbox_pk
        primary key (id);

