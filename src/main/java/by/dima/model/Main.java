package by.dima.model;


import by.dima.model.data.CollectionController;
import by.dima.model.data.abstracts.model.Models;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.services.files.io.ScannerWrapper;
import by.dima.model.data.services.files.io.create.Creatable;
import by.dima.model.data.services.files.io.create.CreateFile;
import by.dima.model.data.services.files.io.read.ReadFileBufferReader;
import by.dima.model.data.services.files.io.read.ReadableFile;
import by.dima.model.data.services.files.io.write.WriteFileOutputStreamWriter;
import by.dima.model.data.services.files.io.write.WriteableFile;
import by.dima.model.data.services.files.parser.string.impl.ParserFromJsonJacksonImpl;
import by.dima.model.data.services.files.parser.string.impl.ParserToJsonJacksonImpl;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.data.services.generate.id.IdGenerateMy;
import by.dima.model.data.services.generate.id.IdGenerateble;
import by.dima.model.server.ServerUDP;
import by.dima.model.server.ServerUDPNonBlocking;
import by.dima.model.server.Serverable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static String FILE_PATH;

    public static void main(String[] args) {

        if (System.getenv("FILE_PATH") == null) {
            FILE_PATH = System.getProperty("user.dir") + '/' + "save";
        } else {
            FILE_PATH = System.getenv("FILE_PATH") + '/' + "save";
        }
        System.out.println("Путь сохранения вашего файла: " + FILE_PATH);
        WriteableFile writeableFile = new WriteFileOutputStreamWriter(FILE_PATH);
        Creatable creatable = new CreateFile(writeableFile);
        creatable.fileCreator();
        ReadableFile readableFile = new ReadFileBufferReader(FILE_PATH);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        ParserFromJson<Models> parserFromJson = new ParserFromJsonJacksonImpl(mapper);
        ParserToJson parserToJson = new ParserToJsonJacksonImpl(mapper);

        try {
            String jsonContent = readableFile.getContent();

            Models models = parserFromJson.getModels(jsonContent);


            CollectionController collectionController = new CollectionController(models, writeableFile, parserToJson);
            IdGenerateble idGenerateble = new IdGenerateMy(collectionController);

            ScannerWrapper scannerWrapper = new ScannerWrapper();
            CommandManager manager = new CommandManager(collectionController, scannerWrapper, parserToJson, idGenerateble);


            //TODO: сделать объект сервера,
            // который принимает объект manager
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
            Serverable serverUDP = (Serverable) context.getBean("server");
            serverUDP.startServer();

            context.close();

        } catch (IOException e) {
            System.err.println("Не удалось получить путь для сохранения объектов!");
        }

//
//
    }
}
