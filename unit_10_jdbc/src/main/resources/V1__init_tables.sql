create table if not exists locations
(
    id        serial       PRIMARY KEY,
    "name"    varchar(50)  UNIQUE NOT NULL,
);

create table if not exists routes
(
    id        serial       PRIMARY KEY,
    cost    integer     not null check(cost >= 0 AND cost < 200000),
    from_id   integer   not null,
    to_id   integer     not null,
    constraint "from_id_fkey" foreign key (from_id) references locations (id),
    constraint "to_id_fkey" foreign key (to_id) references locations (id)
);

create table if not exists problems
(
    id        serial       PRIMARY KEY,
    from_id   integer   not null,
    to_id   integer     not null,
    constraint "from_id_fkey" foreign key (from_id) references locations (id),
    constraint "to_id_fkey" foreign key (to_id) references locations (id)
);

create table if not exists solutions
(
    problem_id   integer  not null constraint "solution_pkey" primary key,
    "cost"   integer,
    constraint "problem_id_fkey" foreign key (problem_id) references problems (id),

);

INSERT INTO locations(name) VALUES ('gdansk');
INSERT INTO locations(name) VALUES ('bydgoszcz');
INSERT INTO locations(name) VALUES ('torun');
INSERT INTO locations(name) VALUES ('warszawa');

INSERT INTO routes(cost, from_id, to_id) VALUES (1, 1, 2);
INSERT INTO routes(cost, from_id, to_id) VALUES (3, 1, 3);
INSERT INTO routes(cost, from_id, to_id) VALUES (1, 2, 3);
INSERT INTO routes(cost, from_id, to_id) VALUES (4, 2, 4);
INSERT INTO routes(cost, from_id, to_id) VALUES (1, 3, 4);

INSERT INTO problems(from_id, to_id) VALUES (1, 4);
INSERT INTO problems(from_id, to_id) VALUES (2, 4);