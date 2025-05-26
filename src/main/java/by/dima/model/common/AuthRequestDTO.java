package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.io.Serial;
import java.io.Serializable;

/**
 * Трансферный объект для передачи команд для авторизированного пользователя по сети
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UserModel userModel;
    private CommandDTO commandDTO;
    private boolean isAuthenticated = true;


}

