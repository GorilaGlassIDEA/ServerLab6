package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserModel implements Serializable {
    private Integer id;
    private String username;
    private String name;
    private String password;

    /**
     * Конструктор для авторизации
     * @param username
     * @param password
     */

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
