package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Данная команда реализует группировку элементов коллекции по любому признаку, описание группировки
 * можно узнать в классе {@link by.dima.model.data.group.model.Groups}
 */
public class GroupCountingByIdCommand extends CommandAbstract {
    private final CollectionController collectionController;

    public GroupCountingByIdCommand(CollectionController collectionController) {
        super("group_counting_by_id", "Group collection elements by the value of the id field and display the number of elements in each group.");
        this.collectionController = collectionController;

    }

    @Override
    public void execute() {
        Map<String, List<Route>> groups = collectionController.getObjGroup().getMapGroup();
        Set<String> groupKeys = groups.keySet();
        for (String key : groupKeys) {
            System.out.println("Route object in group with name \"" + key + "\" : " + groups.get(key).size());
        }
    }
}
