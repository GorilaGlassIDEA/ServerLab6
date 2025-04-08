package by.dima.model.data.route.model.main;

import java.util.List;

/**
 * Данный интерфейс является абстракцией для нормализации данных
 * при сравнении Route моделей.
 *
 * @see Route
 */
public interface NormalisationValue {
    double normalize(Number number);

    void setNumbers(List<Number> numbers);

    List<Double> normalizeAll(List<Number> numbers);
}
