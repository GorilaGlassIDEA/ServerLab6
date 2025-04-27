package by.dima.model.server;

import by.dima.model.common.AnswerDTO;
import by.dima.model.common.CommandDTO;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.common.CommandDTOWrapper;
import by.dima.model.server.executor.ExecuteCommand;
import by.dima.model.server.request.serealizible.ParserAnswerDTOToBytes;
import by.dima.model.server.request.serealizible.ParserBytesToCommandDTO;
import by.dima.model.server.request.serealizible.ParserBytesToObj;
import by.dima.model.server.request.serealizible.ParserObjToBytes;
import lombok.Getter;
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
public class ServerUDPNonBlocking implements Serverable {
    private final Logger logger;
    private final CommandManager commandManager;
    @Getter
    private final int thisPort = 8932;
    @Getter
    private int clientPort = -1;


    public ServerUDPNonBlocking(CommandManager commandManager, Logger logger) {
        this.commandManager = commandManager;
        this.logger = logger;
    }


    public void startServer() {
        ParserBytesToObj<CommandDTO> bytesParser = new ParserBytesToCommandDTO(logger);
        ParserObjToBytes<AnswerDTO> answerParser = new ParserAnswerDTOToBytes(logger);

        ExecuteCommand executeCommand = new ExecuteCommand(commandManager, logger);
        ByteBuffer byteBufferRecieve = ByteBuffer.allocate(1024);

        try (DatagramChannel channel = DatagramChannel.open();
             Selector selector = Selector.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(thisPort));
            channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                try {
                    logger.log(Level.CONFIG, "I am listening...");
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        AnswerDTO answerDTO;
                        SocketAddress address;
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            byteBufferRecieve.clear();
                            address = channel.receive(byteBufferRecieve);
                            logger.log(Level.INFO, "Ip address client: " + address);
                            logger.log(Level.CONFIG, "Data client" + ByteBuffer.wrap(byteBufferRecieve.array(), 0, byteBufferRecieve.limit()));

                            byteBufferRecieve.flip();
                            CommandDTOWrapper commandDTOWrapper = new CommandDTOWrapper(bytesParser.getObj(byteBufferRecieve));
                            byteBufferRecieve.clear();

                            logger.log(Level.INFO, "Command: " + commandDTOWrapper.getNameCommand());

                            answerDTO = executeCommand.execute(commandDTOWrapper);
                            logger.log(Level.INFO, "Command is executed: " + commandDTOWrapper.getNameCommand());
                            if (answerDTO != null) {
                                ByteBuffer byteBufferSend = answerParser.getBytes(answerDTO);
                                if (address != null) {
                                    channel.send(byteBufferSend, address);
                                    logger.log(Level.CONFIG, "Ответ " + answerDTO + " отправлен клиенту по адресу: " + address);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Непредвиденная ошибка класса: " + getClass().getName() + ": " + Arrays.toString(e.getStackTrace()));
                }
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
        }

    }

}
