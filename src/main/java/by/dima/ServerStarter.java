package by.dima;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class ServerStarter {
    public static void main(String[] args) {

        byte[] bytes = new byte[10];
        int len = bytes.length;
        int port = 80;

        try {
            System.out.println("Сервер запущен");
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(bytes, len);

            socket.receive(packet);
            System.out.println(Arrays.toString(bytes));

        } catch (IOException e) {
            System.err.println("Не удалось установить соединение!");
        }
    }
}