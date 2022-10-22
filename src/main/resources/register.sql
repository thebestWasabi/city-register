drop table if exists city_register_address_person;
drop table if exists city_register_person;
drop table if exists city_register_address;
drop table if exists city_register_street;
drop table if exists city_register_district;

create table city_register_district
(
    district_code integer      not null,
    district_name varchar(300) not null,
    primary key (district_code)
);

insert into city_register_district (district_code, district_name)
values (1, 'Выборгский район');

create table city_register_street
(
    street_code integer      not null,
    street_name varchar(300) not null,
    primary key (street_code)
);

insert into city_register_street (street_code, street_name)
VALUES (1, 'Сампсониевский проспект');

create table city_register_address
(
    address_id    serial      not null,
    district_code integer     not null,
    street_code   integer     not null,
    building      varchar(10) not null,
    extension     varchar(10),
    apartment     varchar(10),
    primary key (address_id),
    foreign key (district_code) references city_register_district (district_code) on delete restrict,
    foreign key (street_code) references city_register_street (street_code) on delete restrict
);

insert into city_register_address (district_code, street_code, building, extension, apartment)
values (1, 1, '10', '2', '121');
insert into city_register_address (district_code, street_code, building, extension, apartment)
values (1, 1, '271', null, 4);

create table city_register_person
(
    person_id          serial,
    sur_name           varchar(100) not null,
    given_name         varchar(100) not null,
    patronymic         varchar(100) not null,
    date_of_birth      date         not null,
    passport_series    varchar(10),
    passport_number    varchar(10),
    passport_date      date,
    certificate_number varchar(10),
    certificate_date   date,
    primary key (person_id)
);

insert into city_register_person (sur_name, given_name, patronymic, date_of_birth,
                                  passport_series, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильев', 'Павел', 'Николаевич', '1995-03-18', '1234', '123456', '2015-04-11', null, null);

insert into city_register_person (sur_name, given_name, patronymic, date_of_birth,
                                  passport_series, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильева', 'Ирина', 'Петрована', '1997-08-21', '4321', '654321', '2017-09-19', null, null);

insert into city_register_person (sur_name, given_name, patronymic, date_of_birth,
                                  passport_series, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильева', 'Евгения', 'Павловна', '2016-01-11', null, null, null, '456123', '2016-01-21');

insert into city_register_person (sur_name, given_name, patronymic, date_of_birth,
                                  passport_series, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильев', 'Александр', 'Павлович', '2018-10-24', null, null, null, '321654', '2018-11-09');

create table city_register_address_person
(
    person_address_id          serial,
    address_id                 integer not null,
    person_id                  integer not null,
    start_date_of_registration date    not null,
    end_date_of_registration   date,
    temporal boolean default false,
    primary key (person_address_id),
    foreign key (address_id) references city_register_address (address_id) on delete restrict,
    foreign key (person_id) references city_register_person (person_id) on delete restrict
);

insert into city_register_address_person (address_id, person_id, start_date_of_registration, end_date_of_registration)
values (1 , 1, '2014-10-12', null);
insert into city_register_address_person (address_id, person_id, start_date_of_registration, end_date_of_registration)
values (2 , 2, '2014-10-12', null);
insert into city_register_address_person (address_id, person_id, start_date_of_registration, end_date_of_registration)
values (1 , 3, '2016-02-05', null);
insert into city_register_address_person (address_id, person_id, start_date_of_registration, end_date_of_registration)
values (1 , 4, '2018-12-11', null);