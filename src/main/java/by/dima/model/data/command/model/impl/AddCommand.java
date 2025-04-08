package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;
import by.dima.model.service.generate.id.IdGenerateble;

public class AddCommand extends CommandAbstract {

    private final CollectionController collectionController;
    private final IdGenerateble idGenerateble;

    public AddCommand(CollectionController collectionController, IdGenerateble idGenerateble) {
        super("add", "Add random elem!");
        this.collectionController = collectionController;
        this.idGenerateble = idGenerateble;
    }

    @Override
    public void execute() {
        String name = "name";
        Coordinates coordinates = new Coordinates(10, 10D);
        LocationFrom from = new LocationFrom(10d, 10f, "LocationFrom");
        LocationTo to = new LocationTo(10d, 10d, "LocationTo");
        double distance = 10D;
        for (int i = 0; i < 10; i++) {
            collectionController.addElem(
                    new Route(idGenerateble.generateId(),
                            name,
                            coordinates,
                            from,
                            to,
                            distance));
        }
    }
}
