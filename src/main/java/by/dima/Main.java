package by.dima;

import by.dima.model.dto.CommandDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("Main.class");
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    public static void main(String[] args) {
        consoleHandler.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.FINE);


        boolean isRunning = true;
        DatagramPacket packet;
        int port = 80;
        byte[] byteBuffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (isRunning) {
                packet = new DatagramPacket(byteBuffer, byteBuffer.length);
                logger.log(Level.INFO, "Слушаю клиентов...");
                socket.receive(packet);
                try (ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer, 0, packet.getLength());
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    CommandDTO commandDTO = (CommandDTO) ois.readObject();
                    logger.log(Level.FINE, "Полученный по сети объект " + commandDTO);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Не удалось прочитать данные!");
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Не удалось найти класс для десериализации!");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Порт уже занят!");
        }
    }
}
