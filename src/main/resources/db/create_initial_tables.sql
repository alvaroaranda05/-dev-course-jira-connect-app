-- Create tables

create table if not exists atlassian_host
(
    client_key                 varchar(255) not null
        constraint atlassian_host_pkey
            primary key,
    addon_installed            boolean      not null,
    base_url                   varchar(255),
    created_by                 varchar(255),
    created_date               timestamp,
    description                varchar(255),
    last_modified_by           varchar(255),
    last_modified_date         timestamp,
    oauth_client_id            varchar(255),
    product_type               varchar(255),
    public_key                 varchar(512),
    service_entitlement_number varchar(255),
    shared_secret              varchar(255)
);