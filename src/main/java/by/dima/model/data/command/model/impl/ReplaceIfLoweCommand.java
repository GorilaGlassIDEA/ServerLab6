package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;


/**
 * Класс реализующий команду, которая заменяет {@link Route} если значение нового Route меньше
 * это проверка происходит с помощью реализации интерфейса {@link Comparable} в Route
 */
public class ReplaceIfLoweCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private final ParserFromJson<Route> parser;
    private StringBuilder builder;

    public ReplaceIfLoweCommand(ParserFromJson<Route> parser, UsersCollectionController usersCollectionController) {
        super("replace_if_lowe", "Replace the value by key if the new value is less than the old one.");
        this.usersCollectionController = usersCollectionController;
        this.parser = parser;
        this.builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        if (getCommandDTO().getArgCommand() != null) {
            CollectionController collectionController = new CollectionController(
                    usersCollectionController.getCollectionDTO(Long.parseLong(getCommandDTO().getArgCommand()))
            );
            Route route = parser.getModels(getCommandDTO().getJsonRouteObj());
            if (collectionController.replaceRouteForKey(route)) {
                builder.append("Route с id равное: ").append(route.getId()).append(" был изменен!");
            } else {
                builder.append("Route с id равное: ").append(route.getId()).append(" не был изменен!");
            }
        } else {
            builder.append("Невозможно удалить элемент без id!");
        }
    }

    @Override
    public String getAnswer() {
        usersCollectionController.saveCollection();
        return new String(builder);
    }
}
