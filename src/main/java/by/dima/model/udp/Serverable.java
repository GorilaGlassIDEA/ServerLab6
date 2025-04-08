package by.dima.model.udp;

import java.net.DatagramPacket;

public interface Serverable {

    boolean makeGet();

    byte[] getData();

    void makePost(DatagramPacket packet);

}
