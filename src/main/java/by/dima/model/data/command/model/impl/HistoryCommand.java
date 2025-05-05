package by.dima.model.data.command.model.impl;

import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Команда показывающая историю использования последних 8 команд
 */
@Getter
@Setter
public class HistoryCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public HistoryCommand(UsersCollectionController usersCollectionController) {
        super("history", "Display the last 8 commands.");
        this.usersCollectionController = usersCollectionController;
        this.builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        List<String> commandList = usersCollectionController.getCommandNameList(getCommandDTO().getUserID());

        if (commandList.isEmpty()) {
            builder.append("Your list is empty!");
        } else {
            builder.append("||");
            for (int i = 0; i < commandList.size(); i++) {
                builder.append(" ").append(i + 1).append(" ").append(commandList.get(i)).append(" ||");
            }
            builder.append("\n");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
