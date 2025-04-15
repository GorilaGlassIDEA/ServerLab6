package by.dima.model.server;

import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
public class ServerUDPNonBlocking implements Serverable {
    Logger logger;


    public void startServer() {
        try (DatagramChannel channel = DatagramChannel.open();
             Selector selector = Selector.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(6676));
            channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                logger.log(Level.CONFIG, "I am listening...");
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketAddress address = channel.receive(buffer);
                        buffer.flip();
                        logger.log(Level.INFO, "Ip address client: " + address);
                        logger.log(Level.CONFIG, "Data client" + ByteBuffer.wrap(buffer.array(), 0, buffer.limit()));
                    }
                }
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
        }

    }

}
