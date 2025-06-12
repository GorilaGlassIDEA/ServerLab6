package by.dima.model.data.command.model.impl;

import by.dima.model.common.UserModel;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.services.iterator.RouteDistanceSortIterator;
import lombok.Setter;
import org.w3c.dom.ls.LSOutput;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Данная команда выводит все элементы коллекции отсортированные по убыванию поля distance модели {@link Route}
 */
@Deprecated
public class PrintFieldDescendingDistanceCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;
    @Setter
    private UserModel userModel;

    public PrintFieldDescendingDistanceCommand(UsersCollectionController usersCollectionController) {
        super("print_field_descending_distance", "Print the values of the distance field of all elements in descending order.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        List<Route> routeList = userModel.getRoutesList();
        //TODO: доделать
        routeList.stream()
                .sorted(Comparator.comparingDouble(Route::getDistance))
                .peek(thisRoute ->
                        builder.append("Route c id = ").append(thisRoute.getId()).append(" имя которого: \"").append(thisRoute.getName()).append("\" - имеет дистанцию: ").append(thisRoute.getDistance()).append("\n"))
                .forEach(x->{/* побочный эффект */});
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
