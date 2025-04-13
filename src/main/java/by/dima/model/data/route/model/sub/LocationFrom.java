package by.dima.model.data.route.model.sub;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationFrom {
    private double x;
    private Float y;
    private String name;

    public LocationFrom(double x, Float y, String name) {
        if (y == null || name == null || name.length() > 690) {
            //TODO: сделать Logger
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
