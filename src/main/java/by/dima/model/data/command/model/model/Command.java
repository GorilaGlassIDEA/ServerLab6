package by.dima.model.data.command.model.model;

import by.dima.model.common.CommandDTO;

/**
 * Этот интерфейс является абстракцией для каждой реализации команды
 *
 * @see by.dima.model.data.command.model.CommandManager
 */
public interface Command extends Nameable, Helpable {
    void execute();

    default void setCommandDTO(CommandDTO commandDTO) {
    }


    String getAnswer();

}
