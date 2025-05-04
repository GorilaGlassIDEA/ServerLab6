package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Команда выводящая информацию по всем элементам коллекции на данный момент
 */
@Getter
public class InfoCommand extends CommandAbstract {
    private StringBuilder builder;
    private final UsersCollectionController usersCollectionController;

    public InfoCommand(UsersCollectionController usersCollectionController) {
        super("info", "Show collection details (type, initialization date, size).");
        this.usersCollectionController = usersCollectionController;
        builder = new StringBuilder();
    }


    @Override
    public void execute() {
        CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(getCommandDTO().getUserID()));
        CollectionDTO models = collectionController.getModels();
        builder = new StringBuilder();
        if (models.sizeArray() == 0) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append("Type: ").append(models.getType()).append("\n");
            builder.append("Date: ").append(models.getZonedDateTime()).append("\n");
            builder.append("Size: ").append(models.sizeArray());
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
