package by.dima.model.data.route.model.sub.exceptions;

public class IncorrectDataCoordinatesException extends RuntimeException {
    public IncorrectDataCoordinatesException() {
        super("Проверьте введенные координаты!");
    }
}
