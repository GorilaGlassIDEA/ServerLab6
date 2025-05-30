create table location_from
(
    id   SERIAL PRIMARY KEY,
    x    DOUBLE PRECISION NOT NULL,
    y    float4           not null,
    name varchar(690)     not null
)