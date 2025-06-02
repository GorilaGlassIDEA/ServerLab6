package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.db.dao.DatabaseSavingService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Данная команда показывает полную информацию по всем элементам коллекции
 */
@Getter
@Setter
public class ShowCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;
    private Integer userId;
    private UserModel userModel;


    public ShowCommand(UsersCollectionController usersCollectionController) {
        super("show", "Display all elements in the collection.");
        this.usersCollectionController = usersCollectionController;
    }

    @Override
    public void execute() {
        userId = userModel.getId();
        List<Route> routeList = usersCollectionController.getRoutesForUser(userModel);
        builder = new StringBuilder();
        if (routeList.isEmpty()) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append(usersCollectionController.getRoutesForUser(userModel));
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
