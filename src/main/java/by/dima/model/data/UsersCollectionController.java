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

@ToString
public class UsersCollectionController {
    private UsersCollectionDTO usersCollectionDTO;


    private final WriteableFile writeableFile;
    private final ParserToJson<UsersCollectionDTO> parserToJson;
    private final ParserFromJson<UsersCollectionDTO> parserFromJson;
    private CollectionDTO collectionDTO;

    public UsersCollectionController(ReadableFile readableFile, ParserFromJson<UsersCollectionDTO> parserFromJson, WriteableFile writeableFile, ParserToJson<UsersCollectionDTO> parserToJson) {
        this.writeableFile = writeableFile;
        this.parserToJson = parserToJson;
        this.parserFromJson = parserFromJson;
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

    public CollectionDTO getNowCollectionDTO() {
        //TODO потом убрать временное решечние для тестов ссылок
        return collectionDTO;
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
