package by.dima.model.log;

import lombok.Setter;

import javax.swing.event.ChangeListener;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class LoggerWrapper {
    private Logger logger;

    public void initLogger() {
        logger = Logger.getLogger("server-log");
    }

    public void destroyLogger() {
        for (Handler handler : logger.getHandlers()) {
            handler.close();
        }
    }

}
