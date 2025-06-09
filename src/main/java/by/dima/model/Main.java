package by.dima.model;


import by.dima.model.common.UserModel;
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
import by.dima.model.db.dao.DatabaseSavingService;
import by.dima.model.db.dao.UserDatabaseFacade;
import by.dima.model.db.dao.UserFacadeableDatabase;
import by.dima.model.db.hibernate.config.HibernateConfiguration;
import by.dima.model.locale.LocalizationSettings;
import by.dima.model.utils.log.FactoryLogger;
import by.dima.model.server.ServerUDPNonBlocking;
import by.dima.model.server.Serverable;
import by.dima.model.server.request.parser.RouteParserFromJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.SessionFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {
    public static final Logger logger = FactoryLogger.create();

    public static void main(String[] args) {

        String FILE_PATH;
        if (System.getenv("FILE_PATH") == null) {
            FILE_PATH = System.getProperty("user.dir") + '/' + "save.json";
        } else {
            FILE_PATH = System.getenv("FILE_PATH") + '/' + "save.json";
        }

//        System.out.println("Путь сохранения вашего файла: " + FILE_PATH);
        //TODO: убрать сохранения файлов в текстовом формате

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

        SessionFactory sessionFactory = HibernateConfiguration.getFactory();

        UserFacadeableDatabase<Route> userFacadeableDatabase = new UserDatabaseFacade(sessionFactory);

        final ResourceBundle resourceBundle = LocalizationSettings.installLangResource(new Locale("ru"));
        try {
            UsersCollectionController usersCollectionController = new UsersCollectionController(new DatabaseSavingService(sessionFactory), userFacadeableDatabase, logger,
                    readableFile, parserFromJson, writeableFile, parserToJson);

            CommandManager manager = new CommandManager(resourceBundle, logger, sessionFactory, usersCollectionController, new ParserToJsonImpl<>(mapper), parserFromJsonRoute);
            Serverable serverUDP = new ServerUDPNonBlocking(resourceBundle, userFacadeableDatabase, manager, mapper, logger);
            serverUDP.startServer();

        } catch (RuntimeException e) {
            System.err.println(resourceBundle.getString("exception.main"));
        } finally {
            for (Handler handler : logger.getHandlers()) {
                handler.close();
            }
            HibernateConfiguration.closeSessionFactory();
        }


    }
}
