package by.dima.model.data.route.model.main;

import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;

import java.time.ZonedDateTime;

/**
 * Данных интерфейс является абстракцией паттерна Builder для создания объектов типа {@link Route}
 */
interface RouteBuildable {
    RouteBuildable setId(long id);

    RouteBuildable setName(String name);

    RouteBuildable setCoordinates(Coordinates coordinates);

    RouteBuildable setLocationFrom(LocationFrom locationFrom);

    RouteBuildable setLocationTo(LocationTo locationTo);

    RouteBuildable setDistance(Double distance);

    RouteBuildable setCreationDate(ZonedDateTime zonedDateTime);

    Route build();

}
