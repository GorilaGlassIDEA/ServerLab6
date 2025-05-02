package by.dima.model.server.executor;

import by.dima.model.common.AnswerDTO;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.common.CommandDTOWrapper;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
@Setter
public class ExecuteCommand {
    private CommandManager manager;
    private Logger logger;

    public ExecuteCommand(CommandManager manager, Logger logger) {
        this.manager = manager;
        this.logger = logger;
    }

    public AnswerDTO execute(CommandDTOWrapper commandDTOWrapper) {
        AnswerDTO answerDTO = new AnswerDTO();
        try {
            answerDTO = manager.executeCommand(commandDTOWrapper.getCommandDTO());
            logger.log(Level.FINE, "Действие в классе ExecuteCommand выполнено! Ответ: " + answerDTO);
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Ошибка в результате работы класса CommandManager");
        }
        return answerDTO;
    }

}
