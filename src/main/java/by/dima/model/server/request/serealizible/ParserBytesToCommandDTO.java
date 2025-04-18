package by.dima.model.server.request.serealizible;

import by.dima.model.common.CommandDTO;
import by.dima.model.common.CommandDTOWrapper;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Реализовать DI через xml beans
 */
@NoArgsConstructor
@Setter
public class ParserBytesToCommandDTO implements ParserBytesToObj<CommandDTOWrapper> {
    private ByteBuffer byteBuffer;
    private Logger logger;


    @Override
    public CommandDTOWrapper getObj() {
        CommandDTOWrapper wrapper = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            CommandDTO commandDTO = (CommandDTO) ois.readObject();
            wrapper = new CommandDTOWrapper(commandDTO);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Класс для преобразования потока байтов в объект не найден!");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Не удалось создать поток чтения из прешедшего потока байтов!");
        }
        return wrapper;
    }
}
