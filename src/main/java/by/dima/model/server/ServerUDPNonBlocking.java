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
import org.w3c.dom.ls.LSOutput;

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
    private static final Log log = LogFactory.getLog(ServerUDPNonBlocking.class);
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


                                switch (authorizationRequestDTO.getAuthList()) {
                                    case GET_STATUS -> {
                                        if (facadeableDatabase.isAuthorization(userModel)) {
                                            answerDTO.setAuth(AuthList.AUTHORIZATION);
                                            answerDTO.setAnswer("Пользователь авторизирован");
                                        } else if (facadeableDatabase.isExist(userModel)) {
                                            answerDTO.setAuth(AuthList.IS_EXIST);
                                            answerDTO.setAnswer("Пользователь существует в базе данных (возможно неверно введен пароль)");
                                        } else {
                                            answerDTO.setAuth(AuthList.NOT_EXIST);
                                            answerDTO.setAnswer("Пользователь с таким username не существует!");
                                        }
                                        logger.log(Level.INFO, "Отправлен статус " + answerDTO.getAuth());
                                    }
                                    case REQUEST_REGISTER -> {
                                        if (!facadeableDatabase.isExist(userModel)) {
                                            answerDTO.setUserModel(facadeableDatabase.authentication(userModel));
                                            if (facadeableDatabase.isExist(userModel)) {
                                                answerDTO.setAuth(AuthList.AUTHORIZATION);
                                                answerDTO.setAnswer("Пользователь успешно создан!");
                                            } else {
                                                answerDTO.setAuth(AuthList.NONE);
                                                answerDTO.setAnswer("Не удалось добавить пользователя, возможно ошибка в базе данных!");
                                            }
                                        } else {
                                            answerDTO.setAuth(AuthList.IS_EXIST);
                                            answerDTO.setAnswer("Пользователь с таким именем уже существует!");
                                        }
                                    }
                                    case AUTHORIZATION -> {
                                        if (facadeableDatabase.isAuthorization(authorizationRequestDTO.getUserModel())) {
                                            answerDTO.setAuth(AuthList.AUTHORIZATION);
                                            break;
                                        } else {
                                            answerDTO.setAuth(AuthList.UNAUTHORIZED);
                                            authorizationRequestDTO.setAuthList(AuthList.UNAUTHORIZED);
                                            continue;
                                        }
                                    }
                                    case UNAUTHORIZED -> {
                                        if (facadeableDatabase.isExist(userModel)) {
                                            if (facadeableDatabase.isAuthorization(userModel)) {
                                                answerDTO.setAuth(AuthList.AUTHORIZATION);
                                                answerDTO.setAnswer("Пользователь авторизирован!");
                                                userModel = facadeableDatabase.authorization(userModel);
                                            } else {
                                                answerDTO.setAuth(AuthList.UNAUTHORIZED);
                                                answerDTO.setAnswer("Пользователь не авторизован!");
                                            }
                                        } else {
                                            answerDTO.setAuth(AuthList.NOT_EXIST);
                                            answerDTO.setAnswer("Пользователь не существует");
                                        }
                                    }
                                    default -> {
                                        System.out.println(authorizationRequestDTO);
                                        answerDTO.setAuth(AuthList.NONE);
                                        answerDTO.setAnswer("Не соблюдено API проверьте код!");
                                    }
                                }
                                if (authorizationRequestDTO.getCommandDTO() == null) {
                                    authorizationRequestDTO.setCommandDTO(new CommandDTO());
                                }
                                if (answerDTO.getAuth() == AuthList.AUTHORIZATION) {
                                    userModel = facadeableDatabase.authorization(userModel);
                                }
                                answerDTO.setUserModel(userModel);
                                System.out.println("ServerUDPNonBlocking говорит что после регистрации answer dto = " + answerDTO);


                            } catch (Exception e) {
                                e.printStackTrace();
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
                                        thisCommand.setUserId(answerDTO.getUserModel().getId());
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
                                answerDTO.setAnswer("Неправильный логин или пароль!");
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
