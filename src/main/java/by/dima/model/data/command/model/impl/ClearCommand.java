package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.CommandAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * Данная команда очищает все данные из структуры Map с помощью класса {@link CollectionController}
 * который позволяет данному классу получать актуальную ссылку на данные и очищать их
 */

@Getter
public class ClearCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private StringBuilder builder;

    public ClearCommand(UsersCollectionController usersCollectionController) {
        super("clear", "Clear command helps you with clearing collection!");
        this.usersCollectionController = usersCollectionController;
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        Long userId = getCommandDTO().getUserID();
        if (userId != null) {
            if (!usersCollectionController.deleteDataFromCollection(userId)) {
                builder.append("Комадна ничего не удалила, возможно ваша коллекция пуста!");
            }
        }
        builder.append("Команда выполнена!");
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
