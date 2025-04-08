package by.dima.model.data.route.model;

import by.dima.model.data.route.model.exceptions.IncorrectDataModel;
import by.dima.model.data.route.model.main.Route;

/**
 * Данный класс используется для проверки валидности данных при создании модели {@link Route}
 * в нормальной работе программы не должен быть создан. Он исключает возможность изменение элементов коллекции
 * или создании с помощью рефлексии
 */
public class CheckValidateInfoUtilMyImpl implements CheckableValidateInfoUtil {
    Route route;

    public CheckValidateInfoUtilMyImpl(Route route) {
        this.route = route;
    }

    @Override
    public void checkable() {
        if (route.getName() == null || route.getName().isEmpty()) {
            throw new IncorrectDataModel("Name cannot be null or empty.");
        }

        if (route.getCoordinates() == null) {
            throw new IncorrectDataModel("Coordinates cannot be null.");
        }

        if (route.getFrom() == null) {
            throw new IncorrectDataModel("From cannot be null.");
        }

        if (route.getTo() == null) {
            throw new IncorrectDataModel("To cannot be null.");
        }

        if (route.getDistance() < 1.0) {
            throw new IncorrectDataModel("Distance must be greater than or equal to 1.0.");
        }
    }
}
