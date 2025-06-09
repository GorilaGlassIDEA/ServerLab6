package by.dima.model.server;

import by.dima.model.common.*;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.impl.HelpCommand;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.db.dao.UserFacadeableDatabase;
import by.dima.model.server.request.serealizible.ParserAnswerDTOToBytes;
import by.dima.model.server.request.serealizible.ParserFromBytesToObject;
import by.dima.model.server.request.serealizible.ParserBytesToObj;
import by.dima.model.server.request.serealizible.ParserObjToBytes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
public class ServerUDPNonBlocking implements Serverable {
    private final Logger logger;
    private final CommandManager commandManager;
    @Getter
    private final int thisPort = 8932;

    private final ObjectMapper mapper;
    private UserFacadeableDatabase<Route> facadeableDatabase;
    private ExecutorService processThreadPool = Executors.newCachedThreadPool();
    private final ResourceBundle bundle;


    public ServerUDPNonBlocking(ResourceBundle bundle, UserFacadeableDatabase<Route> facadeableDatabase, CommandManager commandManager, ObjectMapper mapper, Logger logger) {
        this.commandManager = commandManager;
        this.logger = logger;
        this.mapper = mapper;
        this.facadeableDatabase = facadeableDatabase;
        this.bundle = bundle;
    }


    public void startServer() {
        ParserBytesToObj<AuthRequestDTO> bytesParser = new ParserFromBytesToObject<>(logger);
        ParserObjToBytes<AnswerDTO> answerParser = new ParserAnswerDTOToBytes(logger);
        ByteBuffer byteBufferReceive = ByteBuffer.allocate(100000);

        try (DatagramChannel channel = DatagramChannel.open();
             Selector selector = Selector.open()) {
            Scanner scanner = new Scanner(System.in);

            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(thisPort));
            channel.register(selector, SelectionKey.OP_READ);


            while (true) {
                try {
                    selector.select(100);
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SocketAddress address;
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            byteBufferReceive.clear();
                            address = channel.receive(byteBufferReceive);
                            logger.log(Level.INFO, "Ip address client: " + address);
                            logger.log(Level.CONFIG, "Data client" + ByteBuffer.wrap(byteBufferReceive.array(), 0, byteBufferReceive.limit()));
                            TaskForThreads taskForThreads = new TaskForThreads(bundle, byteBufferReceive, address, channel, bytesParser, answerParser, facadeableDatabase, commandManager, mapper, Executors.newFixedThreadPool(10), Executors.newCachedThreadPool(), logger);
                            processThreadPool.submit(taskForThreads);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.log(Level.SEVERE, "Непредвиденная ошибка класса: " + getClass().getName() + ": " + Arrays.toString(e.getStackTrace()));
                }
                if (System.in.available() > 0) {
                    logger.log(Level.FINE, "Начался ввод с клавиатуры!");
                    String input = scanner.nextLine();
                    if (input.equals("exit")) {
                        logger.log(Level.INFO, "Сервер остановлен");
                        return;
                    }
                }
            }

        } catch (
                Exception e) {
            logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
        }

    }

}
