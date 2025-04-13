package by.dima.model.server.udp;

import java.net.DatagramPacket;

public interface Serverable {

    boolean makeGet();

    byte[] getData();

    void makePost(DatagramPacket packet);

}
