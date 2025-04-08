package by.dima.model.data.route.model.exceptions;

/**
 * Ошибка ввода некорректных данных
 */
public class IncorrectDataModel extends RuntimeException {
    public IncorrectDataModel(String message) {
        super(message);
    }
}
