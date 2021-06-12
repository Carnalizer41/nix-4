create table if not exists "courses" (
    id      serial      not null primary key,
    name    varchar     not null unique
);

create table if not exists "teachers" (
    id          serial      not null primary key,
    first_name  varchar     not null,
    last_name   varchar     not null
);

create table if not exists "subjects" (
    id      serial      not null primary key,
    name    varchar     not null unique
);

create table if not exists "marks" (
    id      serial      not null primary key,
    mark    decimal     not null default 0
);

create table if not exists "groups" (
    id          serial      not null primary key,
    name        varchar     not null unique,
    course_id   bigint      not null references courses (id),
    teacher_id  bigint      not null references teachers (id)
);

create table if not exists "students" (
    id          serial      not null primary key,
    first_name  varchar     not null,
    last_name   varchar     not null,
    mark_id     bigint      not null references marks(id),
    group_id    bigint      not null references groups(id)

);

create table if not exists "lessons" (
    id          serial      not null primary key,
    "date"      date        not null,
    "time"      time        not null,
    teacher_id  bigint      not null references teachers(id),
    subject_id  bigint      not null references subjects(id),
    group_id    bigint      not null references groups(id)
);
insert into courses (name) values ('Java');
insert into courses (name) values ('.NET');

insert into teachers (first_name, last_name) values ('Dmytro', 'Shnurenko');
insert into teachers (first_name, last_name) values ('Alexandr', 'Scherbakov');

insert into subjects (name) values ('Java core');
insert into subjects (name) values ('Hibernate');
insert into subjects (name) values ('Spring');
insert into subjects (name) values ('C# core');
insert into subjects (name) values ('Reflections');

insert into marks (mark) values ('1');
insert into marks (mark) values ('2');
insert into marks (mark) values ('3');
insert into marks (mark) values ('4');
insert into marks (mark) values ('5');
insert into marks (mark) values ('6');
insert into marks (mark) values ('7');
insert into marks (mark) values ('8');
insert into marks (mark) values ('9');
insert into marks (mark) values ('10');

insert into groups (name, course_id, teacher_id) values ('nix-4', '1', '1');
insert into groups (name, course_id, teacher_id) values ('.net-2020', '2', '2');

insert into students (first_name, last_name, mark_id, group_id) values ('Ivan', 'Ivanov', '5', '1');
insert into students (first_name, last_name, mark_id, group_id) values ('Katerina', 'Gorelova', '7', '1');
insert into students (first_name, last_name, mark_id, group_id) values ('Sergey', 'Sergeev', '1', '1');
insert into students (first_name, last_name, mark_id, group_id) values ('Sofia', 'Stalina', '3', '2');
insert into students (first_name, last_name, mark_id, group_id) values ('Petr', 'Petrov', '9', '2');
insert into students (first_name, last_name, mark_id, group_id) values ('Ivan', 'Susanin', '2', '2');

insert into lessons (date, time, teacher_id, subject_id, group_id) values ('12.06.2021','19:00:00', '1','5','2');
insert into lessons (date, time, teacher_id, subject_id, group_id) values ('10.06.2021','19:00:00','1' ,'4','2');
insert into lessons (date, time, teacher_id, subject_id, group_id) values ('23.06.2021','11:00:00','1' ,'3','1');
insert into lessons (date, time, teacher_id, subject_id, group_id) values ('13.06.2021','19:00:00', '2','2','1');
insert into lessons (date, time, teacher_id, subject_id, group_id) values ('29.05.2021','11:00:00', '2','1','1');