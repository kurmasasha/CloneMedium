package ru.javamentor.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Collection<User> listOfUsers;

    @Override
    public String getAuthority() {
        return name;
    }
}
