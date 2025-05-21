package by.dima.model.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class UserModel {
    private String username;
    private String name;
    private String password;
}
