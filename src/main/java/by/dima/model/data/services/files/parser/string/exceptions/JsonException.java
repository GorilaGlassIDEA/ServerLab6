package by.dima.model.data.services.files.parser.string.exceptions;

// cause - исключение, ставшее причиной выброса данного
public class JsonException extends RuntimeException {
    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
