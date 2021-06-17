create table if not exists "users"
(
    id             serial not null primary key,
    email          varchar  not null unique,
    phone          varchar(15) not null unique
);

create table if not exists "accounts"
(
    id                         serial  not null primary key,
    user_id                    bigint  not null,
    constraint "user_id_fkey" foreign key (user_id) references users (id)
);

create table if not exists "category_types"(
    id                         serial  not null primary key,
    name                       varchar not null unique
);

create table if not exists "categories"
(
    id                         serial  not null primary key,
    name                       varchar not null unique,
    type_id                    bigint  not null references category_types (id)
);

create table if not exists "transactions"
(
    id                         serial  not null primary key,
    account_id                 bigint  not null references accounts (id),
    date                       timestamp not null default current_timestamp,
    amount                     decimal  not null,
    description                varchar  not null,
    check (amount > 0)
);

create table if not exists "transaction_categories"
(
    id                         serial  not null primary key,
    transaction_id             bigint  not null references transactions (id),
    category_id                bigint  not null references categories (id)
);


INSERT INTO public.users(email, phone) VALUES ('postgres@mail.com', '+380991234567');
INSERT INTO public.accounts(user_id) VALUES (1);
INSERT INTO public.accounts(user_id) VALUES (1);
INSERT INTO public.category_types(name) VALUES ('Income');
INSERT INTO public.category_types(name) VALUES ('Expense');
INSERT INTO public.categories(name, type_id) VALUES ('Salary', 1);
INSERT INTO public.categories(name, type_id) VALUES ('Donate', 1);
INSERT INTO public.categories(name, type_id) VALUES ('Food', 2);
INSERT INTO public.categories(name, type_id) VALUES ('Clothes', 2);
INSERT INTO public.categories(name, type_id) VALUES ('Car fuel', 2);
INSERT INTO public.categories(name, type_id) VALUES ('Rent', 2);

