package by.dima.model.data.route.model.main;


import by.dima.model.data.route.model.CheckValidateInfoUtilMyImpl;
import by.dima.model.data.route.model.CheckableValidateInfoUtil;
import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс представляет собой основную модель для хранения данных в коллекции
 * @see by.dima.model.data.CollectionController
 * @see by.dima.model.data.abstracts.model.Models
 */
@Getter
@Setter
@NoArgsConstructor
public class Route implements Comparable<Route> {
    private long id;
    private String name;
    private Coordinates coordinates;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime creationDate;
    private LocationFrom from;
    private LocationTo to;
    private double distance;

    public Route(long id, String name, Coordinates coordinates, LocationFrom from, LocationTo to, double distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
        // generate id
        this.id = id;
        CheckableValidateInfoUtil checkableValidateInfoUtil = new CheckValidateInfoUtilMyImpl(this);
        checkableValidateInfoUtil.checkable();
    }

    @Override
    public int compareTo(Route o) {
        NormalisationValue normalisationValue = new NormalizeValueImpl();

        ArrayList<Number> valuesLocationListThis = new ArrayList<>(List.of(
                this.to.getX(),
                this.to.getY(),
                this.from.getX(),
                this.from.getY()
        ));
        ArrayList<Number> valuesLocationListThat = new ArrayList<>(List.of(
                o.to.getX(),
                o.to.getY(),
                o.from.getX(),
                o.from.getY()
        ));
        List<Number> allValues = new ArrayList<>();
        allValues.addAll(valuesLocationListThat);
        allValues.addAll(valuesLocationListThis);
        normalisationValue.setNumbers(allValues);

        double sumThat = 0;
        double sumThis = 0;
        for (Double num : normalisationValue.normalizeAll(valuesLocationListThis)) {
            sumThis += num;
        }
        for (Double num : normalisationValue.normalizeAll(valuesLocationListThat)) {
            sumThat += num;
        }


        return Double.compare(sumThis, sumThat);
    }

    @Override
    public String toString() {


        return "Route {" +
                "\n\tID: " + id +
                ",\n\tName: '" + name + '\'' +
                ",\n\tCoordinates: " + coordinates +
                ",\n\tCreation Date: " + creationDate +
                ",\n\tFrom: " + from +
                ",\n\tTo: " + to +
                ",\n\tDistance: " + distance +
                "\n}";
    }

}
