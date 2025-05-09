package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    private ExecuteDTO executeDTO;

    public CommandDTO(String nameCommand, String argCommand, String jsonRouteObj, Long userID) {
        this.nameCommand = nameCommand;
        this.argCommand = argCommand;
        this.jsonRouteObj = jsonRouteObj;
        this.userID = userID;
    }
}



