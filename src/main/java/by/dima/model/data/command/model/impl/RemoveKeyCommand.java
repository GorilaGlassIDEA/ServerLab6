package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет удалить элемент по id, если такой id существует в коллекции
 */
@Getter
@Setter
public class RemoveKeyCommand extends CommandAbstract {

    private final CollectionController collectionController;
    private Long arg;

    public RemoveKeyCommand(CollectionController collectionController) {
        super("remove_key", "Remove an element by its key.");
        this.collectionController = collectionController;
    }


    @Override
    public void execute() {
        collectionController.removeElem(arg);
        System.out.println("Команда выполнена!");
    }

    @Override
    public void setArgs(String arg) {
        try {
            this.arg = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            System.err.println("Incorrect arg!");
        }
    }
}
