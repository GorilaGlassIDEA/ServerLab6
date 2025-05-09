package by.dima.model.common.route.sub;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LocationFrom implements Serializable {
    private double x;
    private Float y;
    private String name;
    @Serial
    private static final long serialVersionUID = 1L;
    public LocationFrom(double x, Float y, String name) {
        if (y == null || name == null || name.length() > 690) {
            //TODO: сделать Logger
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
