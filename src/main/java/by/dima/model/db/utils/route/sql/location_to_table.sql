create table location_to
(
    id   serial primary key,
    x    double precision not null,
    y    double precision not null,
    name varchar(330)     not null
)