package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.CreateRouteUsingScanner;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.route.model.main.RouteBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет обновить элемент по указанному id
 */
public class UpdateCommand extends CommandAbstract {
    @Getter
    @Setter
    private Long id;
    private final CreateRouteUsingScanner routeCreator;
    private final CollectionController collectionController;

    public UpdateCommand(CreateRouteUsingScanner routeCreator, CollectionController collectionController) {
        super("update", "Update an element by its ID.");
        this.routeCreator = routeCreator;
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        if (id != null) {
            Route newRoute = routeCreator.createRoute(new RouteBuilder(), id);
            collectionController.updateElem(newRoute);
        }
    }

    @Override
    public void setArgs(String arg) {
        try {
            this.id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            System.out.println(id);
            System.err.println("Invalid input! Try again!");
        }
    }
}
