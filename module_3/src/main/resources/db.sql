create table "users"
(
    id             identity not null primary key,
    name           varchar  not null,
    email          varchar  not null unique,
    phone          varchar(15) not null unique
);

create table "accounts"
(
    id                         bigint  not null primary key,
    user_id                    bigint  not null,
    constraint "user_id_fkey" foreign key (user_id) references users (id)
);

create table "transactions"
(
    id                         bigint  not null primary key,
    resource_id                bigint  not null references managed_resources (id),
    previous_resource_capacity bigint  not null,
    capacity                   bigint  not null,
    issued_at                  timestamp not null default current_timestamp,
    status                     tinyint not null,
    reason                     varchar,
    check (previous_resource_capacity >= 0)
);

create table "income_category"
(
    id                         bigint  not null primary key,
    resource_id                bigint  not null references managed_resources (id),
    previous_resource_capacity bigint  not null,
    capacity                   bigint  not null,
    issued_at                  timestamp not null default current_timestamp,
    status                     tinyint not null,
    reason                     varchar,
    check (previous_resource_capacity >= 0)
);

create table "expense_category"
(
    id                         bigint  not null primary key,
    resource_id                bigint  not null references managed_resources (id),
    previous_resource_capacity bigint  not null,
    capacity                   bigint  not null,
    issued_at                  timestamp not null default current_timestamp,
    status                     tinyint not null,
    reason                     varchar,
    check (previous_resource_capacity >= 0)
);
