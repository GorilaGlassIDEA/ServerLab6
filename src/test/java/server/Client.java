package server;

import java.io.IOException;
import java.net.*;

public class Client implements Clientable {

    private int port = 80;

    InetAddress inetAddress;

    public Client() throws IOException {
        inetAddress = InetAddress.getLocalHost();
    }

    @Override
    public void makePost(byte[] data) {


        try {
            DatagramPacket dp = new DatagramPacket(data, data.length, inetAddress, port);
            DatagramSocket ds = new DatagramSocket();
            ds.send(dp);

        } catch (IOException e) {
            System.err.println("Не удалось отправить данные!");
            e.printStackTrace();
        }


    }

    @Override
    public byte[] makeGet() {
        return new byte[0];
    }
}
