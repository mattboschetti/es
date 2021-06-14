alter table event_store rename constraint sourced_events_pk to event_store_pk;

alter index sourced_events_id_uindex rename to event_store_id_uindex;

alter index sourced_events_stream_version_uindex rename to event_store_stream_version_uindex;

