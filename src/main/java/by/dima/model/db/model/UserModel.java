package by.dima.model.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserModel {
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
