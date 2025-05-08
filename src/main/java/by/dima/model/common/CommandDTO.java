package by.dima.model.common;

import by.dima.model.common.main.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Трансферный объект для передачи команд по сети
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nameCommand;
    private String argCommand;
    private String jsonRouteObj;
    private Long userID;
    private List<Route> commandDTOS;

    public CommandDTO(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    public CommandDTO(String nameCommand, String argCommand, String jsonRouteObj, Long userID) {
        this.nameCommand = nameCommand;
        this.argCommand = argCommand;
        this.jsonRouteObj = jsonRouteObj;
        this.userID = userID;
        this.commandDTOS = null;

    }

    public void clear() {
        nameCommand = null;
        argCommand = null;
        jsonRouteObj = null;
        userID = null;
        commandDTOS = null;
    }

}



