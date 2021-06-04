create table outbox
(
    id uuid not null,
    created_at timestamp not null
);

create unique index outbox_id_uindex
    on outbox (id);

alter table outbox
    add constraint outbox_pk
        primary key (id);

