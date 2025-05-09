package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.common.route.main.Route;

import java.util.List;
import java.util.Map;

/**
 * Данная команда реализует группировку элементов коллекции по любому признаку, описание группировки
 * можно узнать в классе {@link by.dima.model.data.group.model.Groups}
 */
public class GroupCountingByIdCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public GroupCountingByIdCommand(UsersCollectionController usersCollectionController) {
        super("group_counting_by_id", "Group collection elements by the value of the id field and display the number of elements in each group.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
        Map<String, List<Route>> groups = collectionController.getObjGroup().getMapGroup();
        for (String key : groups.keySet()) {
            builder.append("Route объектов в категории \"").append(key).append("\" : ").append(groups.get(key).size()).append(" шт").append("\n");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
