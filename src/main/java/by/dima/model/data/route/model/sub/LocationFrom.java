package by.dima.model.data.route.model.sub;

import by.dima.model.data.route.model.sub.exceptions.IncorrectDataLocationException;
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
            throw new IncorrectDataLocationException();
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
