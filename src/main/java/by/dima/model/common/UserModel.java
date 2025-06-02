package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "userRoutLinkList")
@Table(name = "users")
public final class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRouteLink> userRouteLinkList;
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Конструктор для авторизации
     *
     * @param username
     * @param password
     */

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Route> getRoutesList() {
        return userRouteLinkList.stream()
                .map(UserRouteLink::getRoute)
                .collect(Collectors.toList());
    }
    public void deleteAllRouteForThisUser(){
        userRouteLinkList.clear();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(username, userModel.username) && Objects.equals(password, userModel.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
