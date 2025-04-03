package by.dima;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerStarterTCP {


    public static void main(String[] args) throws IOException {

        int port = 80;
        ServerSocket serverSocket;
        Socket socket;
        InputStream is;
        OutputStream os;
        byte[] bytes = new byte[10];

        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();

        is = socket.getInputStream();
        is.read(bytes);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i]*=2;
        }
        System.out.println("Получен запрос: " + Arrays.toString(bytes));
        os = socket.getOutputStream();
        os.write(bytes);

    }

}
