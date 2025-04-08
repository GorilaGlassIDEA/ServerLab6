package by.dima;

import by.dima.model.dto.CommandDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger("Main.class");
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();
    private static FileHandler fileHandler;

    public static void main(String[] args) {

        try {
            fileHandler = new FileHandler("app.log");
        } catch (IOException e) {
            System.err.println("Работа вашей программы будет без отслеживания ошибок!");
        }
        fileHandler.setLevel(Level.FINE);
        fileHandler.setFormatter(new SimpleFormatter());

        consoleHandler.setLevel(Level.FINE);
        consoleHandler.setFilter(r -> (r.getLevel() == Level.INFO || r.getLevel() == Level.FINE));
        consoleHandler.setFormatter(new SimpleFormatter());

        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
        logger.addHandler(consoleHandler);

        logger.setLevel(Level.FINE);

        boolean isRunning = true;
        DatagramPacket packet;
        int port = 80;
        byte[] byteBuffer = new byte[1024];

        logger.log(Level.INFO, "Ожидание запросов от клиентов!");
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (isRunning) {
                packet = new DatagramPacket(byteBuffer, byteBuffer.length);
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
        } finally {
            consoleHandler.close();
            fileHandler.close();
        }
    }
}
