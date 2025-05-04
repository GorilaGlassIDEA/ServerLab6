package by.dima.model.data;

import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.abstracts.model.UsersCollectionDTO;
import by.dima.model.data.services.files.io.read.ReadableFile;
import by.dima.model.data.services.files.io.write.WriteableFile;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import lombok.ToString;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@ToString
public class UsersCollectionController {
    private UsersCollectionDTO usersCollectionDTO;


    private final WriteableFile writeableFile;
    private final ParserToJson<UsersCollectionDTO> parserToJson;
    private final ParserFromJson<UsersCollectionDTO> parserFromJson;
    private CollectionDTO collectionDTO;
    private final Logger logger;

    public UsersCollectionController(Logger logger, ReadableFile readableFile, ParserFromJson<UsersCollectionDTO> parserFromJson, WriteableFile writeableFile, ParserToJson<UsersCollectionDTO> parserToJson) {
        this.writeableFile = writeableFile;
        this.parserToJson = parserToJson;
        this.parserFromJson = parserFromJson;
        this.logger = logger;
        try {
            usersCollectionDTO = parserFromJson.getModels(readableFile.getContent());
        } catch (IOException e) {
            usersCollectionDTO = new UsersCollectionDTO(new HashMap<>());
            //TODO: прологировать ошибку при пустом map в файле json
        }
    }

//    public void addCollection(Long userId, CollectionDTO collectionDTO) {
//        if (usersCollectionDTO.getMap() != null) {
//            usersCollectionDTO.edit(userId, collectionDTO);
//            writeableFile.write(parserToJson.getJson(usersCollectionDTO));
//        } else {
//            System.out.println("Не удалось добавить новую модель CollectionDTO");
//        }
//    }


    public CollectionDTO getCollectionDTO(Long id) {
        collectionDTO = usersCollectionDTO.getCollection(id);
        if (collectionDTO == null) {
            collectionDTO = new CollectionDTO(new HashMap<>());
            usersCollectionDTO.edit(id, collectionDTO);
        }
        return collectionDTO;
    }


    public boolean deleteDataFromCollection(Long userId) {
        if (usersCollectionDTO.getMap().containsKey(userId)) {
            usersCollectionDTO.getMap().remove(userId);
            return true;
        } else {
            return false;
        }
    }

    public boolean saveCollection() {
        try {
            if (usersCollectionDTO == null) {
                writeableFile.write(parserToJson.getJson(new UsersCollectionDTO(new HashMap<>())));
            } else {
                writeableFile.write(parserToJson.getJson(usersCollectionDTO));
            }
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
