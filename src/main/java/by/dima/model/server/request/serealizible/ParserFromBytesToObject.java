package by.dima.model.server.request.serealizible;

import by.dima.model.common.CommandDTO;
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
@Setter
public class ParserFromBytesToObject<T> implements ParserBytesToObj<T> {
    private Logger logger;

    public ParserFromBytesToObject(Logger logger) {
        this.logger = logger;
    }


    @Override
    public T getObj(ByteBuffer byteBuffer) {
        T t = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            t = (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Класс для преобразования потока байтов в объект не найден!");
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Не удалось создать поток чтения из пришедшего потока байтов!");
        }
        return t;
    }
}
