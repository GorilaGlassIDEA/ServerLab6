create table user_link_route
(
    id      SERIAL PRIMARY KEY,
    userId  INT NOT NULL,
    routeId INT NOT NULL,
    FOREIGN KEY (userId) references users,
    FOREIGN KEY (routeId) references routes
)
