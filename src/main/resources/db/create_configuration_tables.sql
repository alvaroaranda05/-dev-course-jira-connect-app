-- Create tables

create table if not exists configuration
(
    key            varchar(255) not null constraint configuration_pkey primary key,
    value          varchar(255),
    client_key     varchar(255) not null
);