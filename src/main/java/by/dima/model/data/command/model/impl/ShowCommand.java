package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда показывает полную информацию по всем элементам коллекции
 */
@Getter
@Setter
public class ShowCommand extends CommandAbstract {

    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public ShowCommand(UsersCollectionController usersCollectionController) {
        super("show", "Display all elements in the collection.");
        this.usersCollectionController = usersCollectionController;
    }

    @Override
    public void execute() {
        CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
        builder = new StringBuilder();
        CollectionDTO models = collectionController.getModels();
        if (models.sizeArray() == 0) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append(collectionController.getModels());
        }
    }
    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
