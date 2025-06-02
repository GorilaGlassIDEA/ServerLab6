package by.dima.model.data.command.model.impl;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ShowAllCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;
    private UserModel userModel;


    public ShowAllCommand(UsersCollectionController usersCollectionController) {
        super("show_all", "Display all elements in the all collections.");
        this.usersCollectionController = usersCollectionController;
    }

    @Override
    public void execute() {
        List<Route> routeList = usersCollectionController.getRoutesForUser(userModel);
        builder = new StringBuilder();
        if (routeList.isEmpty()) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append(usersCollectionController.getAllRoute());
            builder.append("Успешно получены все записи для всех пользователей!");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}


