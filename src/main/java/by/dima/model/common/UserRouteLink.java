package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "id")
@Table(name = "user_link_route")
public final class UserRouteLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserModel userModel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "routeid")
    private Route route;

}
