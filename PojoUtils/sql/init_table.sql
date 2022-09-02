# create database myTest;
drop table if exists user;
create table user
(
    user_id    BIGINT primary key,
    username   varchar(255) not null,
    password   varchar(255) not null,
    email      varchar(255) not null,
    status     tinyint(1)   not null,
    created_at datetime     not null default current_timestamp
);
create index user_index1 on user (username);

drop table if exists staff;
create table staff
(
    staff_id   BIGINT primary key,
    salary     int         not null,
    role       varchar(18),
    dept       varchar(18),
    corp       varchar(18) not null,
    created_at datetime    not null default current_timestamp
);

insert into staff (staff_id, salary, role, dept, corp)
values (1, 100, 'cleaner', 'A', 'corp1');
insert into staff (staff_id, salary, role, dept, corp)
values (2, 200, 'manager', 'A', 'corp1');
insert into staff (staff_id, salary, role, dept, corp)
values (3, 150, 'worker', 'A', 'corp1');
