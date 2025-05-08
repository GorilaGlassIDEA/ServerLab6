package by.dima.model.common.main;

import java.io.Serializable;
import java.util.List;

/**
 * Данный интерфейс является абстракцией для нормализации данных
 * при сравнении Route моделей.
 *
 * @see Route
 */
public interface NormalisationValue extends Serializable {
    double normalize(Number number);

    void setNumbers(List<Number> numbers);

    List<Double> normalizeAll(List<Number> numbers);
}
