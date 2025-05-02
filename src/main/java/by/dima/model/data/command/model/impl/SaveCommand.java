package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда позволяет сохранить все элементы коллекции в json формате по пути переменной окружения
 */
@Getter
@Setter
public class SaveCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder stringBuilder;

    public SaveCommand(UsersCollectionController usersCollectionController) {
        super("save", "Save the collection to a file.");
        this.usersCollectionController = usersCollectionController;
    }

    @Override
    public void execute() {
        stringBuilder = new StringBuilder();

        if (usersCollectionController.saveCollection()) {
            stringBuilder.append("Your changes was saving!");
        } else {
            stringBuilder.append("Your changes wasn't saving!");
        }
    }

    @Override
    public String getAnswer() {
        return new String(stringBuilder);
    }
}
