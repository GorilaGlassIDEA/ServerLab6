package by.dima.model.data.route.model.main;

import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;

import java.time.ZonedDateTime;

/**
 * Данный класс является реализацией паттерна Builder для создания объектов типа {@link Route}
 * @see RouteBuildable
 */
public class RouteBuilder implements RouteBuildable {

    private long id;
    private String name;
    private Coordinates coordinates;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime creationDate;
    private LocationFrom locationFrom;
    private LocationTo locationTo;
    private double distance;


    @Override
    public RouteBuilder setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public RouteBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public RouteBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public RouteBuilder setLocationFrom(LocationFrom locationFrom) {
        this.locationFrom = locationFrom;
        return this;
    }

    @Override
    public RouteBuilder setLocationTo(LocationTo locationTo) {
        this.locationTo = locationTo;
        return this;
    }

    @Override
    public RouteBuilder setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public RouteBuilder setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @Override
    public Route build() {
        Route route = new Route(id, name, coordinates, locationFrom, locationTo, distance);
        if (creationDate == null) {
            creationDate = ZonedDateTime.now();
        }
        route.setCreationDate(creationDate);
        return route;
    }


}
