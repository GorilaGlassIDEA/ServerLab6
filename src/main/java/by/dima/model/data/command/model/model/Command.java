package by.dima.model.data.command.model.model;

import by.dima.model.common.CommandDTO;

/**
 * Этот интерфейс является абстракцией для каждой реализации команды
 *
 * @see by.dima.model.data.command.model.CommandManager
 */
public interface Command extends Nameable, Helpable {
    void execute();

    default void setArgs(String arg) {
        // default method for command which has more than one arg (update {id} insert {id})
    }

    default void setCommandDTO(CommandDTO commandDTO) {
        // убрать setArgs(String arg) после того как все команды начнут использовать setCommandDTO().getArg()
    }


    default String getAnswer() {
        return "Данная команда не выдает ответа";
    }

}
