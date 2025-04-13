package server;

public interface Clientable {

    void makePost(byte[] data);

    byte[] makeGet();

}
