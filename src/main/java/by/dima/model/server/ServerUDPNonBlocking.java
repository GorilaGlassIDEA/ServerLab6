package by.dima.model.server;

import by.dima.model.server.executor.ExecuteCommand;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
@NoArgsConstructor
public class ServerUDPNonBlocking implements Serverable {
    private Logger logger;
    private ExecuteCommand executeCommand;


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
