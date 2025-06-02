package by.dima.model.data;

import by.dima.model.common.UserModel;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.abstracts.model.UsersCollectionDTO;
import by.dima.model.data.services.files.io.read.ReadableFile;
import by.dima.model.data.services.files.io.write.WriteableFile;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.db.dao.DatabaseSavingService;
import by.dima.model.db.dao.UserFacadeableDatabase;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ToString
public class UsersCollectionController {
    private UsersCollectionDTO usersCollectionDTO;


    private final WriteableFile writeableFile;
    private final ParserToJson<UsersCollectionDTO> parserToJson;
    private final ParserFromJson<UsersCollectionDTO> parserFromJson;
    private CollectionDTO collectionDTO;
    private final Logger logger;
    private final UserFacadeableDatabase<Route> facadeableDatabase;
    private final DatabaseSavingService databaseSavingService;

    public UsersCollectionController(DatabaseSavingService databaseSavingService, UserFacadeableDatabase<Route> facadeableDatabase, Logger logger, ReadableFile readableFile, ParserFromJson<UsersCollectionDTO> parserFromJson, WriteableFile writeableFile, ParserToJson<UsersCollectionDTO> parserToJson) {
        this.writeableFile = writeableFile;
        this.parserToJson = parserToJson;
        this.parserFromJson = parserFromJson;
        this.logger = logger;
        this.facadeableDatabase = facadeableDatabase;
        this.databaseSavingService = databaseSavingService;
        try {
            usersCollectionDTO = parserFromJson.getModels(readableFile.getContent());
        } catch (IOException e) {
            usersCollectionDTO = new UsersCollectionDTO(new HashMap<>());
        }
    }

    @Deprecated
    public CollectionDTO getCollectionDTO(Long userId) {
        collectionDTO = usersCollectionDTO.getCollection(userId);
        if (collectionDTO == null) {
            collectionDTO = new CollectionDTO(new HashMap<>());
            usersCollectionDTO.edit(userId, collectionDTO);
        }
        return collectionDTO;
    }

    @Deprecated
    public void addCommandName(String commandName, Long userId) {
        collectionDTO = getCollectionDTO(userId);
        collectionDTO.addCommandHistory(commandName);
    }

    @Deprecated
    public List<String> getCommandNameList(Long userId) {
        collectionDTO = getCollectionDTO(userId);
        return collectionDTO.getHistoryCommandList();
    }


    public boolean deleteDataFromCollection(UserModel userModel) {
        try {
            databaseSavingService.deleteDataForUser(userModel);
            logger.log(Level.FINE, "Данные пользователя удалены");
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Route> getRouteListForUser(UserModel userModel) {
        return databaseSavingService.getRoutesForUser(userModel);
    }

    public CollectionController getCollectionControllerForUser(UserModel userModel) {
        try {
            return new CollectionController(usersCollectionDTO.getCollection((long) userModel.getId()));
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Id юзера равно null");
            return new CollectionController(new CollectionDTO());
        }
    }

    public boolean saveToCollectionRouteForUser(UserModel userModel, Route route) {
        try {
            databaseSavingService.saveRoute(userModel, route);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Route> getRoutesForUser(UserModel userModel) {
        List<Route> routeList = databaseSavingService.getRoutesForUser(userModel);
        return routeList;
    }

    public List<Route> getAllRoute() {
        return databaseSavingService.getAllRoute();
    }

    public boolean saveToCollectionRouteForUser() {
        //todo:  убрать! это затычка чтобы не было ошибок!
        return true;
    }
}
