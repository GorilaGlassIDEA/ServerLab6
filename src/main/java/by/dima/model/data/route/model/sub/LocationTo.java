package by.dima.model.data.route.model.sub;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationTo {
    private Double x;
    private Double y;
    private String name;

    public LocationTo(Double x, Double y, String name) {
        if (x == null || y == null || name == null || name.length() > 330) {
            //TODO: сделать Logger
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
