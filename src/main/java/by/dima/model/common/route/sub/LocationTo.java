package by.dima.model.common.route.sub;


import by.dima.model.server.Serverable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LocationTo implements Serializable {
    private Double x;
    private Double y;
    private String name;
    @Serial
    private static final long serialVersionUID = 1L;
    public LocationTo(Double x, Double y, String name) {
        if (x == null || y == null || name == null || name.length() > 330) {
            //TODO: сделать Logger
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
