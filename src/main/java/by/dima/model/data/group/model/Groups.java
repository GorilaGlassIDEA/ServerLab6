package by.dima.model.data.group.model;

import by.dima.model.data.CollectionController;
import by.dima.model.data.route.model.main.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Данный класс реализует хранение и сортировку данных коллекции для реализации команды вывода элементов
 * коллекции в заданных группах.
 * @see by.dima.model.data.command.model.impl.GroupCountingByIdCommand
 */
public class Groups {
    private final CollectionController collectionController;

    public Groups(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    public Map<String, List<Route>> getMapGroup() {

        //TODO: переделать под функциональный интерфейс для использования любых условий вывода группы

        Map<String, List<Route>> groups = new HashMap<>();
        List<Route> evenList = new ArrayList<>();
        List<Route> unevenList = new ArrayList<>();
        groups.put("even", evenList);
        groups.put("uneven", unevenList);
        Map<Long, Route> mapFrom = collectionController.getCollectionForControl();


        for (Long key : mapFrom.keySet()) {
            if (key % 2 == 0) evenList.add(mapFrom.get(key));
            else unevenList.add(mapFrom.get(key));
        }

        return groups;
    }
}
