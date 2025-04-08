package by.dima.model.data.route.model.sub;

import by.dima.model.data.route.model.sub.exceptions.IncorrectDataCoordinatesException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Coordinates {
    private int x;
    private Double y;

    //TODO написать тесты
    public Coordinates(int x, Double y) {
        if (y == null || y <= -749) {
            throw new IncorrectDataCoordinatesException();
        }
        this.x = x;
        this.y = y;
    }

}
