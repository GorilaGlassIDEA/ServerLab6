package by.dima.model.common.route.sub;

import by.dima.model.common.route.main.Route;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс является одной из реализаций нормализации данных для сортировки {@link Route}
 */
public class NormalizeValueImpl implements NormalisationValue, Serializable {
    private double max;
    private double min;
    @Setter
    private List<Number> numbers;
    @Serial
    private static final long serialVersionUID = 1L;

    public NormalizeValueImpl() {
        max = -1000000000D;
        min = 1000000000D;
    }

    @Override
    public double normalize(Number t) {
        for (Number number : numbers) {
            double thisNum = number.doubleValue();
            if (thisNum > max) max = thisNum;
            if (thisNum < min) min = thisNum;
        }
        return (t.doubleValue() - min) / (max - min);
    }

    public List<Double> normalizeAll(List<Number> numbers) {
        List<Double> normalizeList = new ArrayList<>();
        for (Number number : numbers) {
            normalizeList.add(normalize(number));
        }
        return normalizeList;
    }
}
