package by.dima.model.data.command.model.impl;

import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;

import by.dima.model.common.route.main.Route;

import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import lombok.Getter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Команда позволяющая добавлять новые элементы в коллекцию
 */
@Getter

public class InsertCommand extends CommandAbstract {
    private final UsersCollectionController usersCollectionController;
    private final Command saveCommand;
    private final Logger logger;
    private final ParserFromJson<Route> parserFromJson;
    private StringBuilder builder;

    public InsertCommand(UsersCollectionController usersCollectionController, ParserFromJson<Route> parserFromJson, Logger logger) {
        super("insert", "Add a new element with a specified key.");
        this.usersCollectionController = usersCollectionController;
        saveCommand = new SaveCommand(usersCollectionController);
        this.logger = logger;
        this.parserFromJson = parserFromJson;
        builder = new StringBuilder();
    }


    @Override
    public void execute() {

        builder = new StringBuilder();

        logger.log(Level.INFO, "Команда которая пришла на выполнение к insertCommand:" + getCommandDTO());
        try {
            if (getCommandDTO() != null && getCommandDTO().getJsonRouteObj()!=null) {
                String arg = getCommandDTO().getArgCommand();

                Long userId = getCommandDTO().getUserID();
                Route route = parserFromJson.getModels(getCommandDTO().getJsonRouteObj());
                final CollectionController collectionController = new CollectionController(usersCollectionController.getCollectionDTO(userId));
                collectionController.addElem(route);
                logger.log(Level.FINE, "Нашлась коллекция для пользователя с id: " + userId + " аргумент равен " + arg);

                if (usersCollectionController.saveCollection()) {
                    logger.log(Level.FINE, "Коллекция сохранила Map<Long id, Route userRoute!> " + collectionController.getCollectionForControl());
                    builder.append("Ваши данные сохранены корректно!").append(collectionController.getModels());
                } else {
                    logger.log(Level.WARNING, "Не удалось сохранить Map<Long id, Route userRoute!>");
                }

            } else {
                logger.log(Level.CONFIG, "CommandDTO не существует! Проверьте метод setCommandDTO класса InsertCommand");
            }
            saveCommand.execute();
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "В команде InsertCommand произошла критическая ошибка ");
            e.printStackTrace();
        }

    }

    @Override
    public String getAnswer() {
        usersCollectionController.saveCollection();
        return new String(builder);
    }
}
