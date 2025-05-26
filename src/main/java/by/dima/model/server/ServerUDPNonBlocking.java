package by.dima.model.server;

import by.dima.model.common.*;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
public class ServerUDPNonBlocking implements Serverable {
    private final Logger logger;
    private final CommandManager commandManager;
    @Getter
    private final int thisPort = 8932;

    private final ObjectMapper mapper;
    private UserFacadeableDatabase facadeableDatabase;


    public ServerUDPNonBlocking(UserFacadeableDatabase facadeableDatabase, CommandManager commandManager, ObjectMapper mapper, Logger logger) {
        this.commandManager = commandManager;
        this.logger = logger;
        this.mapper = mapper;
        this.facadeableDatabase = facadeableDatabase;
    }


    public void startServer() {
        ParserBytesToObj<AuthRequestDTO> bytesParser = new ParserFromBytesToObject<>(logger);
        ParserObjToBytes<AnswerDTO> answerParser = new ParserAnswerDTOToBytes(logger);
        ByteBuffer byteBufferReceive = ByteBuffer.allocate(100000);
        AuthRequestDTO authorizationRequestDTO;

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

                            byteBufferReceive.flip();
                            authorizationRequestDTO = bytesParser.getObj(byteBufferReceive);
                            CommandDTOWrapper commandDTOWrapper;
                            final AnswerDTO answerDTO = new AnswerDTO();

                            try {
                                UserModel userModel = authorizationRequestDTO.getUserModel();
                                logger.log(Level.INFO, "User который пришел от клиента: " + userModel);
                                logger.log(Level.INFO, "Username: " + userModel.getUsername() + "\nPassword: " + userModel.getPassword());
                                if (!authorizationRequestDTO.isAuthenticated()) {
                                    facadeableDatabase.authentication(userModel);
                                    if (facadeableDatabase.isAuthorization(userModel)) {
                                        answerDTO.setAuth(AuthList.AUTHORIZATION);
                                        authorizationRequestDTO.setAuthenticated(true);
                                        logger.log(Level.FINEST, "Создан новый пользователь!");
                                    } else if (facadeableDatabase.isAuthentication(userModel)) {
                                        answerDTO.setAuth(AuthList.UNAUTHORIZED);
                                        authorizationRequestDTO.setAuthenticated(true);
                                        logger.log(Level.FINEST, "Создан новый пользователь, но почему то не зарегистрировался, непредвиденное поведение программы!");
                                    }
                                } else {
                                    if (facadeableDatabase.isAuthorization(userModel)) {
                                        logger.log(Level.INFO, "Пользователь авторизован!");
                                        answerDTO.setAuth(AuthList.AUTHORIZATION);
                                    } else {
                                        logger.log(Level.INFO, "Пользователь не авторизован");
                                        answerDTO.setAuth(AuthList.UNAUTHORIZED);
                                        if (!facadeableDatabase.isAuthentication(userModel)) {
                                            logger.log(Level.INFO, "Пользователь не существует!");
                                            answerDTO.setAuth(AuthList.UNAUTHENTICATED);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                logger.log(Level.INFO, "Проблема с авторизацией класс " + this.getClass().getName());
                            }

                            if (answerDTO.getAuth() == AuthList.AUTHORIZATION) {
                                try {
                                    byteBufferReceive.clear();
                                    commandDTOWrapper = new CommandDTOWrapper(authorizationRequestDTO.getCommandDTO(), mapper);
                                    Map<String, Command> commandMap = commandManager.getCommandMap();
                                    Command thisCommand = new HelpCommand(commandManager);
                                    if (commandMap.containsKey(commandDTOWrapper.getNameCommand())) {
                                        thisCommand = commandMap.get(commandDTOWrapper.getNameCommand());
                                    }
                                    thisCommand.setCommandDTO(commandDTOWrapper.getCommandDTO());
                                    try {
                                        commandManager.execute(thisCommand);
                                        answerDTO.setAnswer(thisCommand.getAnswer());
                                    } catch (RuntimeException e) {
                                        answerDTO.setAnswer("Невозможно выполнить такую команду!");
                                        logger.log(Level.INFO, "Невозможно выполнить execute_script внутри другого!");
                                    }
                                    logger.log(Level.INFO, "Command is executed: " + commandDTOWrapper.getNameCommand());
                                } catch (NullPointerException e) {
                                    logger.log(Level.INFO, "Команда пустая!");
                                    answerDTO.setAnswer("Команда пустая!");
                                }
                            } else {
                                answerDTO.setAnswer("Несанкционированный доступ!");
                            }
                            if (address != null) {
                                ByteBuffer byteBufferSend = answerParser.getBytes(answerDTO);
                                channel.send(byteBufferSend, address);
                                logger.log(Level.CONFIG, "Ответ " + answerDTO + " отправлен клиенту по адресу: " + address);
                            }
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
