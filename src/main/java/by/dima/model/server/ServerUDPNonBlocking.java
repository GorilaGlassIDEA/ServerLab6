package by.dima.model.server;

import by.dima.model.Main;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.common.CommandDTOWrapper;
import by.dima.model.server.executor.ExecuteCommand;
import by.dima.model.server.request.serealizible.ParserBytesToCommandDTO;
import by.dima.model.server.request.serealizible.ParserBytesToObj;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private CommandManager commandManager;

    public ServerUDPNonBlocking(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    public void startServer() {
        ParserBytesToObj<CommandDTOWrapper> bytesParser;

        try (DatagramChannel channel = DatagramChannel.open();
             Selector selector = Selector.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(6676));
            channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                try {
                    logger.log(Level.CONFIG, "I am listening...");
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {

                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketAddress address = channel.receive(buffer);
                            logger.log(Level.INFO, "Ip address client: " + address);
                            logger.log(Level.CONFIG, "Data client" + ByteBuffer.wrap(buffer.array(), 0, buffer.limit()));

                            buffer.flip();
                            bytesParser = (ParserBytesToCommandDTO) Main.context.getBean("bytes-parser");
                            bytesParser.setByteBuffer(buffer);

                            buffer.clear();

                            CommandDTOWrapper commandDTOWrapper = bytesParser.getObj();
                            logger.log(Level.INFO, "Command: " + commandDTOWrapper.getNameCommand());

                            ExecuteCommand executeCommand = (ExecuteCommand) Main.context.getBean("execute-command");
                            executeCommand.setManager(commandManager);
                            executeCommand.execute(commandDTOWrapper);
                            logger.log(Level.INFO, "Command is executed: " + commandDTOWrapper.getNameCommand());

                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Непредвиденная ошибка класса: " + getClass().getName() + ": " + Arrays.toString(e.getStackTrace()));
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
        }

    }

}
