create table coordinates
(
    id serial primary key,
    x  int              not null,
    y  DOUBLE PRECISION not null check ( y>-749 )
)
