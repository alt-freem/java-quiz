create table dept
(
    id   int primary key,
    name varchar(255) not null unique
);

create table emp
(
    name    varchar(255) not null primary key,
    title   varchar(255) not null,
    dept_id int          not null references dept (id),
    sal     int
);

insert into dept (id, name)
values (1, 'sale'),
       (2, 'acc'),
       (3, 'it'),
       (4, 'hr');

insert into emp
    (name, title, dept_id, sal)
values ('John', 'mngr', 1, 3500),
       ('Jim', 'mngr', 2, 4700),
       ('Jordan', 'vp', 3, 7500),
       ('Jane', 'eng', 3, 5500),
       ('Martha', 'mngr', 4, 2500),
       ('Johana', 'vp', 4, 3500),
       ('Jack', 'eng', 3, 3500),
       ('Daniel', 'vp', 2, 8300),
       ('Elon', 'ceo', 1, 17300),
       ('Dick', 'mngr', 1, null);

-- Найти количество сотрудников в каждом отделе

-- Найти отдел и сотрудника с Мин зарплатой

-- Найти отделы в которых средняя зарплата больше 6000


