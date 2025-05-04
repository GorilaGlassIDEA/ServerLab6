package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.ScannerBuildRoute;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.server.request.parser.RouteParserFromJson;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет обновить элемент по указанному id
 */
public class UpdateCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private final ParserFromJson<Route> parser;
    private StringBuilder builder;

    public UpdateCommand(ParserFromJson<Route> parser, UsersCollectionController usersCollectionController) {
        super("update", "Update an element by its ID.");
        this.usersCollectionController = usersCollectionController;
        this.parser = parser;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
        builder = new StringBuilder();

        if (getCommandDTO().getArgCommand() != null) {
            Route newRoute = parser.getModels(getCommandDTO().getJsonRouteObj());
            if (collectionController.updateElem(newRoute)) {
                builder.append("Элемент успешно обновлен!");
            } else {
                builder.append("Не удалось обновить элемент с id: ").append(newRoute.getId()).append(" элемент не существует!");
            }
        } else {
            builder.append("Нет id для команды update, проверьте корректность ввода!");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
