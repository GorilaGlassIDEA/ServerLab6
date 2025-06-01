package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_link_route")
public class UserRouteLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private UserModel userModel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "routeid")
    private Route route;

}
