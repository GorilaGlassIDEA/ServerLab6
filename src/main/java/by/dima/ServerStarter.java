package by.dima;


import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServerStarter {
    public static void main(String[] args) throws IOException {

        DatagramSocket socket;
        DatagramPacket packet;

        byte[] dataInput = new byte[10];
        List<Integer> ports = new ArrayList<>(List.of(65000, 65001, 65002));

        for (Integer port : ports) {
            try {
                socket = new DatagramSocket(port);
                packet = new DatagramPacket(dataInput, dataInput.length);
                socket.receive(packet);
                System.out.println(new String(dataInput, StandardCharsets.UTF_8));

                packet = new DatagramPacket(dataInput, dataInput.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
            } catch (BindException e) {
                System.out.println("порт: " + port + " занят!");
            }
        }

    }
}