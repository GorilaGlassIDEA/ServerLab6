package by.dima.model.common.route.sub;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class Coordinates implements Serializable {
    private int x;
    private Double y;
    @Serial
    private static final long serialVersionUID = 1L;
    //TODO написать тесты
    public Coordinates(int x, Double y) {
        if (y == null || y <= -749) {
            //TODO: сделать logger
        }
        this.x = x;
        this.y = y;
    }

}
