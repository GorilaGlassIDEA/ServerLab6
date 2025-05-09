package by.dima.model.data.services.builder;

import by.dima.model.common.route.main.Route;
import by.dima.model.common.route.sub.Coordinates;
import by.dima.model.common.route.sub.LocationFrom;
import by.dima.model.common.route.sub.LocationTo;

public class RouteBuilder {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocationFrom from;
    private LocationTo to;
    private double distance;

    public RouteBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public RouteBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RouteBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public RouteBuilder setFrom(LocationFrom from) {
        this.from = from;
        return this;
    }

    public RouteBuilder setTo(LocationTo to) {
        this.to = to;
        return this;
    }

    public RouteBuilder setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Route build() {
        return new Route(id, name, coordinates, from, to, distance);
    }
}
