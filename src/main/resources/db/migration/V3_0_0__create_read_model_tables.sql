create table inventory_item_detail
(
    id uuid not null,
    name varchar not null,
    current_count bigint not null,
    version int not null
);

create unique index inventory_item_detail_id_uindex
    on inventory_item_detail (id);

alter table inventory_item_detail
    add constraint inventory_item_detail_pk
        primary key (id);

create table inventory_item_list
(
    id uuid not null,
    name varchar not null
);

create unique index inventory_item_list_id_uindex
    on inventory_item_list (id);

alter table inventory_item_list
    add constraint inventory_item_list_pk
        primary key (id);

