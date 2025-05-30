package by.dima.model.common.route.main;


import by.dima.model.common.route.sub.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Route implements Comparable<Route>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private LocationFrom from;
    private LocationTo to;
    private double distance;
    @Serial
    private static final long serialVersionUID = 1L;

    public Route(long id, String name, Coordinates coordinates, LocationFrom from, LocationTo to, double distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
        // generate id
        this.id = id;
        //TODO: не доделал настройки hibernate
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
