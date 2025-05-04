package by.dima.model.data.command.model.model;

import by.dima.model.common.CommandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Добавляет информации в абстракцию интерфейса {@link Command}
 * Все команды наследуются от данного класса
 */
@Getter
@Setter
public abstract class CommandAbstract implements Command {

    private CommandDTO commandDTO;


    private String key;
    private String help;

    public CommandAbstract(String key, String help) {
        this.key = key;
        this.help = help;
    }


}
