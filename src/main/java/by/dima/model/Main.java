package by.dima.model;


import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.abstracts.model.UsersCollectionDTO;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.services.files.io.create.Creatable;
import by.dima.model.data.services.files.io.create.CreateFile;
import by.dima.model.data.services.files.io.read.ReadFileBufferReader;
import by.dima.model.data.services.files.io.read.ReadableFile;
import by.dima.model.data.services.files.io.write.WriteFileOutputStreamWriter;
import by.dima.model.data.services.files.io.write.WriteableFile;
import by.dima.model.data.services.files.parser.string.impl.ParserToJsonImpl;
import by.dima.model.data.services.files.parser.string.impl.UsersCollectionParserFromJson;
import by.dima.model.data.services.files.parser.string.impl.UsersCollectionParserToJson;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.log.FactoryLogger;
import by.dima.model.server.ServerUDPNonBlocking;
import by.dima.model.server.Serverable;
import by.dima.model.server.request.parser.RouteParserFromJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {
    public static String FILE_PATH;
    public static final Logger logger = FactoryLogger.create();

    public static void main(String[] args) {
        if (System.getenv("FILE_PATH") == null) {
            FILE_PATH = System.getProperty("user.dir") + '/' + "save.json";
        } else {
            FILE_PATH = System.getenv("FILE_PATH") + '/' + "save.json";
        }
        System.out.println("Путь сохранения вашего файла: " + FILE_PATH);
        WriteableFile writeableFile = new WriteFileOutputStreamWriter(FILE_PATH);
        Creatable creatable = new CreateFile(writeableFile);
        creatable.fileCreator();
        ReadableFile readableFile = new ReadFileBufferReader(FILE_PATH);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        ParserFromJson<UsersCollectionDTO> parserFromJson = new UsersCollectionParserFromJson(mapper, logger);
        ParserToJson<UsersCollectionDTO> parserToJson = new UsersCollectionParserToJson(mapper);
        ParserFromJson<Route> parserFromJsonRoute = new RouteParserFromJson(mapper);

        try {

            UsersCollectionController usersCollectionController = new UsersCollectionController(logger,
                    readableFile, parserFromJson, writeableFile, parserToJson
            );

            CommandManager manager = new CommandManager(logger, usersCollectionController, new ParserToJsonImpl<Route>(mapper), parserFromJsonRoute);

            Serverable serverUDP = new ServerUDPNonBlocking(manager, mapper, logger);
            serverUDP.startServer();

            //TODO: исправить ошибку команды info когда коллекция не пустая!

        } catch (RuntimeException e) {
            System.err.println("Не удалось получить путь для сохранения объектов!");
        } finally {
            for (Handler handler : logger.getHandlers()) {
                handler.close();
            }
        }


    }
}
