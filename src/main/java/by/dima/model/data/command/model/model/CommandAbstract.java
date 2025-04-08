package by.dima.model.data.command.model.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Добавляет информации в абстракцию интерфейса {@link Command}
 * Все команды наследуются от данного класса
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class CommandAbstract implements Command {

    private String key;
    private String help;

}
