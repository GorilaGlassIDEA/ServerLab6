package by.dima.model.data.command.model.impl;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Команда выводящая информацию по всем элементам коллекции на данный момент
 */
@Getter
public class InfoCommand extends CommandAbstract {
    private StringBuilder builder;
    private final UsersCollectionController usersCollectionController;
    @Setter
    private UserModel userModel;

    public InfoCommand(UsersCollectionController usersCollectionController) {
        super("info", "Show collection details (type, initialization date, size).");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }


    @Override
    public void execute() {

        List<Route> routeList = usersCollectionController.getRouteListForUser(userModel);
        builder = new StringBuilder();
        if (routeList.isEmpty()) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append("Type: ").append(Route.class.getName()).append("\n");
            builder.append("Size: ").append(routeList.size()).append("\n");
            for (Route route : routeList) {
                builder.append("Date for Route with id = " + routeList.indexOf(route) + ": ").append(route.getCreationDate()).append("\n");
            }
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder).trim();
    }
}
