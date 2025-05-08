package by.dima.model.data.group.model;

import by.dima.model.data.CollectionController;
import by.dima.model.common.main.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Данный класс реализует хранение и сортировку данных коллекции для реализации команды вывода элементов
 * коллекции в заданных группах.
 *
 * @see by.dima.model.data.command.model.impl.GroupCountingByIdCommand
 */
public class Groups {
    private final CollectionController collectionController;

    public Groups(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    public Map<String, List<Route>> getMapGroup() {

        //TODO: переделать под функциональный интерфейс для использования любых условий вывода группы

        List<Route> evenList, unevenList;
        Map<Long, Route> mapFrom = collectionController.getCollectionForControl();
        evenList = mapFrom.entrySet().stream()
                .filter(s -> s.getKey() % 2 == 0)
                .map(Map.Entry::getValue)
                .toList();
        unevenList = mapFrom.entrySet().stream()
                .filter(s -> s.getKey() % 2 == 1)
                .map(Map.Entry::getValue)
                .toList();


        return new HashMap<>(Map.of("even", evenList, "uneven", unevenList));
    }
}
