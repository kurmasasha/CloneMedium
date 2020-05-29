package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String username;

    @Column
    String password;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Role role;

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(Long id, String firstName, String lastName, String username, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @ManyToMany

    @JsonIgnore
//    @JoinTable(name = "user_topic", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="topic_id"))
    private Set<Topic> topicCollection;
=======
    @JoinTable(name = "users_topics", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topicCollection = new HashSet<>();;


    @OneToMany(mappedBy = "authorOfComment", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Comment> allComments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> setRoles = new HashSet<>();
        setRoles.add(new SimpleGrantedAuthority(role.getName()));
        return setRoles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
