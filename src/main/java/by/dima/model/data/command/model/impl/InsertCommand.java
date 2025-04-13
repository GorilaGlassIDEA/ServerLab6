package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;

import by.dima.model.data.route.model.main.Route;

import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.data.services.generate.id.IdGenerateble;
import lombok.Getter;
import lombok.Setter;

/**
 * Команда позволяющая добавлять новые элементы в коллекцию
 */
@Getter
public class InsertCommand extends CommandAbstract {
    @Setter
    private Route route;
    private long id;
    private final CollectionController collectionController;
    private final ParserToJson parser;
    private final IdGenerateble idGenerateble;

    public InsertCommand(CollectionController collectionController, ParserToJson parser, IdGenerateble idGenerateble) {
        super("insert", "Add a new element with a specified key.");
        this.collectionController = collectionController;
        this.parser = parser;
        this.idGenerateble = idGenerateble;
    }


    @Override
    public void execute() {
//        route = fillOutRouteModelUsingScanner.createRoute(new RouteBuilder(), id);
        collectionController.addElem(route);
        System.out.println("New element added in collection! You can save changes using save command!");
    }

    @Override
    public void setArgs(String arg) {
        try {
            id = idGenerateble.generateId(Long.parseLong(arg));
        } catch (NumberFormatException e) {
            id = idGenerateble.generateId();
        }
    }
}
