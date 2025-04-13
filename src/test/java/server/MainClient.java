package server;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {

        byte[] bytes = {1, 2, 3, 4, 5};

        Clientable clientable = new Client();
        clientable.makePost(bytes);
    }

}
