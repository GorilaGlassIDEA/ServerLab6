package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * Трансферный объект для передачи команд для авторизированного пользователя по сети
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationRequestDTO implements Serializable {
    private String username;
    private String password;
    private CommandDTO commandDTO;
}

