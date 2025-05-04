package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.route.model.main.Route;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет удалить элемент по id, если такой id существует в коллекции
 */
@Getter
@Setter
public class RemoveKeyCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public RemoveKeyCommand(UsersCollectionController usersCollectionController) {
        super("remove_key", "Remove an element by its key.");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }


    @Override
    public void execute() {
        CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
        builder = new StringBuilder();
        if (getCommandDTO().getArgCommand() != null) {
            Long routeId = Long.parseLong(getCommandDTO().getArgCommand());
            collectionController.removeElem(routeId);
            builder.append("Элемент с id: ").append(routeId).append(" был удален!");
        } else {
            builder.append("Нет id для команды remove_key, проверьте корректность ввода!");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
