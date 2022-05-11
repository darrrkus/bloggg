package ua.tvsolutions.bloggg.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username", length = 30, nullable = false)
    private String username;
    @Column(name = "password", length = 80, nullable = false)
    private String password;

    @ManyToMany(
//            fetch = FetchType.EAGER
    )
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}
