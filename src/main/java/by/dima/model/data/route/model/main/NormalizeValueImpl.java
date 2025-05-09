package by.dima.model.data.route.model.main;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Данный класс является одной из реализаций нормализации данных для сортировки {@link Route}
 */
public class NormalizeValueImpl implements NormalisationValue {
    private double max;
    private double min;
    @Setter
    private List<Number> numbers;

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
