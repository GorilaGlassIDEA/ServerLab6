package by.dima.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Трансферный объект для передачи команд по сети
 */
@Data
@AllArgsConstructor
public class CommandDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nameCommand;
    private String argCommand;
}
