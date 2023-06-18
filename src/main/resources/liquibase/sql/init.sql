--liquibase formatted sql

--changeSet skryagin:1
create table if not exists users
(
    id         bigserial primary key,
    first_name varchar(255),
    image      varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    phone      varchar(255),
    role       varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['ADMIN'::character varying, 'USER'::character varying])::text[])),
    username   varchar(255)
);

create table if not exists ads
(
    id          bigserial primary key,
    description varchar(255),
    image       varchar(255),
    price       integer,
    title       varchar(255),
    author_id   bigint references users
);

create table if not exists comments
(
    id         bigserial primary key,
    created_at timestamp(6),
    text       varchar(255),
    ad_id      bigint references ads,
    author_id  bigint references users
);

