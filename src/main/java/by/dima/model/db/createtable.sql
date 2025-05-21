CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    name     varchar(100)        not null,
    password TEXT                not null
);