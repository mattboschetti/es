create table inventory_item
(
    id uuid not null,
    name varchar not null,
    category varchar not null,
    unit_price text not null,
    quantity bigint not null,
    version int not null
);

create unique index inventory_item_id_uindex
    on inventory_item (id);

alter table inventory_item
    add constraint inventory_item_pk
        primary key (id);