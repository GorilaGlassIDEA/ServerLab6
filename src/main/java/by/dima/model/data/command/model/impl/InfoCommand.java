package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.abstracts.model.Models;
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

    public InfoCommand(CollectionController collectionController) {
        super("info", "Show collection details (type, initialization date, size).");
        this.collectionController = collectionController;
    }


    @Override
    public void execute() {
        Models models = collectionController.getModels();
        if (models.sizeArray() == 0) {
            System.err.println("Your collections is Empty!\nYou can add new element between insert command!");
        } else {
            System.out.println("Type: " + models.getType());
            System.out.println("Date: " + models.getZonedDateTime());
            System.out.println("Size: " + models.sizeArray());
        }
    }
}
