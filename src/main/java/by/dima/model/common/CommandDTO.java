package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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

    public CommandDTO(String nameCommand) {
        this.nameCommand = nameCommand;
    }
}



