package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Команда выводящая информацию по всем элементам коллекции на данный момент
 */
@Getter
@Setter
public class InfoCommand extends CommandAbstract {
    private CollectionController collectionController;
    private StringBuilder builder;

    public InfoCommand(CollectionController collectionController) {
        super("info", "Show collection details (type, initialization date, size).");
        this.collectionController = collectionController;
        builder = new StringBuilder();
    }


    @Override
    public void execute() {
        CollectionDTO models = collectionController.getModels();
        builder = new StringBuilder();
        if (models.sizeArray() == 0) {
            builder.append("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            builder.append("Type: ").append(models.getType());
            builder.append("Date: ").append(models.getZonedDateTime());
            builder.append("Size: ").append(models.sizeArray());
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
