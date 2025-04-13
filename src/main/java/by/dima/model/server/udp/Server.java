package by.dima.model.server.udp;

import lombok.Getter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Serverable {
    private int port;
    @Getter
    private byte[] data;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public boolean makeGet() {
        DatagramSocket socket;
        DatagramPacket packet;
        byte[] bytes = new byte[1024];

        try {
            socket = new DatagramSocket(port);
            packet = new DatagramPacket(bytes, bytes.length);
            socket.receive(packet);
            this.data = bytes;
            return packet.getLength() > 0;

        } catch (SocketException | IllegalArgumentException e) {

            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.WARNING, "Указанный порт недоступен!");

            throw new RuntimeException();
        } catch (IOException e) {

            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.WARNING, "Не удалось получить пакет от клиента!");

            throw new RuntimeException();

        }
    }

    @Override
    public void makePost(DatagramPacket packet) {

    }
}
