alter table inventory_item_detail
    alter column unit_price type text using unit_price::text;
