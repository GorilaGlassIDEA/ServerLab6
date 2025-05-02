package by.dima.model.log;


import java.io.IOException;
import java.util.logging.*;

public class FactoryLogger {
    public static Logger logger;
    private FactoryLogger(){}

    public static Logger create() {
        logger = Logger.getLogger("client-log");
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        try {
            FileHandler fileHandler = new FileHandler("app.log");
            fileHandler.setLevel(Level.FINEST);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Работа вашей программы будет без отслеживания ошибок!");
        }
        consoleHandler.setLevel(Level.FINE);
//        consoleHandler.setFilter(record -> (record.getLevel() == Level.FINE) || record.getLevel() == Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.FINE);
        return logger;
    }

}
