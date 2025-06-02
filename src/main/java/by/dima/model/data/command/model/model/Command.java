package by.dima.model.data.command.model.model;

import by.dima.model.common.CommandDTO;
import by.dima.model.common.UserModel;

/**
 * Этот интерфейс является абстракцией для каждой реализации команды
 *
 * @see by.dima.model.data.command.model.CommandManager
 */
public interface Command extends Nameable, Helpable {
    void execute();

    default void setCommandDTO(CommandDTO commandDTO) {
    }
    default void setUserId(Integer userId){
        System.out.println("Вызван ментод setUserId интерфейса Command!");
        //TODO: заменить везд на userModel
    }
    default void setUserModel(UserModel userModel){

    }

    String getAnswer();

    CommandDTO getCommandDTO();

}
