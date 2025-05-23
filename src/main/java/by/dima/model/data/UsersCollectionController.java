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
import java.util.List;
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


    public CollectionDTO getCollectionDTO(Long userId) {
        collectionDTO = usersCollectionDTO.getCollection(userId);
        if (collectionDTO == null) {
            collectionDTO = new CollectionDTO(new HashMap<>());
            usersCollectionDTO.edit(userId, collectionDTO);
        }
        return collectionDTO;
    }

    public void addCommandName(String commandName, Long userId) {
        collectionDTO = getCollectionDTO(userId);
        collectionDTO.addCommandHistory(commandName);
    }

    public List<String> getCommandNameList(Long userId) {
        collectionDTO = getCollectionDTO(userId);
        return collectionDTO.getHistoryCommandList();
    }



    public boolean deleteDataFromCollection(Long userId) {
        if (usersCollectionDTO.getMap().containsKey(userId)) {
            usersCollectionDTO.getMap().remove(userId);
            saveCollection();
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
