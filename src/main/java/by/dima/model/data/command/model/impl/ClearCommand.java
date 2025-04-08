package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда очищает все данные из структуры Map с помощью класса {@link CollectionController}
 * который позволяет данному классу получать актуальную ссылку на данные и очищать их
 */

@Getter
@Setter
public class ClearCommand extends CommandAbstract {
    private final CollectionController collectionController;

    /**
     * @param collectionController класс для хранения всех данных в программе
     */
    public ClearCommand(CollectionController collectionController) {
        super("clear", "Clear command helps you with clearing collection!");
        this.collectionController = collectionController;
    }

    @Override
    public void execute() {
        collectionController.resetModels();
        System.out.println("Команда выполнена!");
    }

}
