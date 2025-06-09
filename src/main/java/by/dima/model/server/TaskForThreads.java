package by.dima.model.server;

import by.dima.model.common.*;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.impl.HelpCommand;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.db.dao.UserFacadeableDatabase;
import by.dima.model.server.request.serealizible.ParserBytesToObj;
import by.dima.model.server.request.serealizible.ParserObjToBytes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskForThreads implements Runnable {
    private final ByteBuffer data;
    private final SocketAddress address;
    private final DatagramChannel channel;
    private final ParserBytesToObj<AuthRequestDTO> bytesParser;
    private final ParserObjToBytes<AnswerDTO> answerParser;
    private final UserFacadeableDatabase facadeableDatabase;
    private final CommandManager commandManager;
    private final ObjectMapper mapper;
    private final ExecutorService workPool;
    private final ExecutorService sendPool;
    private final Logger logger;
    public final ResourceBundle bundle;


    public TaskForThreads(ResourceBundle bundle, ByteBuffer data, SocketAddress address, DatagramChannel channel, ParserBytesToObj<AuthRequestDTO> bytesParser, ParserObjToBytes<AnswerDTO> answerParser, UserFacadeableDatabase database, CommandManager commandManager, ObjectMapper mapper, ExecutorService workPool, ExecutorService sendPool, Logger logger) {
        this.data = data;
        this.address = address;
        this.channel = channel;
        this.bytesParser = bytesParser;
        this.answerParser = answerParser;
        this.facadeableDatabase = database;
        this.commandManager = commandManager;
        this.mapper = mapper;
        this.workPool = workPool;
        this.sendPool = sendPool;
        this.logger = logger;
        this.bundle = bundle;
    }

    @Override
    public void run() {
        final AnswerDTO answerDTO = new AnswerDTO();
        workPool.submit(() -> {
            AuthRequestDTO authorizationRequestDTO = bytesParser.getObj(data);
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
                        } else {
                            answerDTO.setAuth(AuthList.UNAUTHORIZED);
                            authorizationRequestDTO.setAuthList(AuthList.UNAUTHORIZED);
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
                    final CommandDTOWrapper commandDTOWrapper = new CommandDTOWrapper(authorizationRequestDTO.getCommandDTO(), mapper);
                    Map<String, Command> commandMap = commandManager.getCommandMap();
                    Command thisCommand = new HelpCommand(commandManager, bundle);
                    if (commandMap.containsKey(commandDTOWrapper.getNameCommand())) {
                        thisCommand = commandMap.get(commandDTOWrapper.getNameCommand());
                    }
                    thisCommand.setCommandDTO(commandDTOWrapper.getCommandDTO());
                    try {
                        thisCommand.setUserModel(answerDTO.getUserModel());
                        commandManager.execute(thisCommand);
                        answerDTO.setAnswer(thisCommand.getAnswer());
                    } catch (RuntimeException e) {
                        e.printStackTrace();
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
            sendPool.submit(() -> {
                try {
                    if (address != null) {
                        ByteBuffer byteBufferSend = answerParser.getBytes(answerDTO);
                        channel.send(byteBufferSend, address);
                        logger.log(Level.CONFIG, "Ответ " + answerDTO + " отправлен клиенту по адресу: " + address);
                    }
                } catch (IOException e) {
                    logger.log(Level.FINE, "Не удалось отправить ответ от сервера клиенту");
                }

            });
        });

    }
}
