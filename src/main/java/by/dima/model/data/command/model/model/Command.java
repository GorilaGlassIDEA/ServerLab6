package by.dima.model.data.command.model.model;

/**
 * Этот интерфейс является абстракцией для каждой реализации команды
 * @see by.dima.model.data.command.model.CommandManager
 */
public interface Command extends Nameable, Helpable {
    void execute();

    default void setArgs(String arg) {
        // default method for command which has more than one arg (update {id} insert {id})
    }
}
