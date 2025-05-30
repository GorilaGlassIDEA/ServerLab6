create table routes
(
    id             SERIAL PRIMARY KEY,
    name           varchar(100)     not null,
    coordinatesId  INT              NOT NULL,
    locationFromId int              not null,
    locationToId   int              not null,
    distance       double precision not null,
    create_time    timestamp        not null DEFAULT current_timestamp,
    foreign key (coordinatesId) references coordinates(id),
    foreign key (locationFromId) references location_from(id),
    foreign key (locationToId) references location_to(id)
)