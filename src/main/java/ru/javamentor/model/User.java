package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.javamentor.util.CustomAuthorityDeserializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс представляющий модель пользователя в системе
 *
 * @version 1.0
 * @author Java Mentor
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column
    private String firstName;

    @NotNull
    @NotEmpty
    @Column
    private String lastName;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;

    @Transient
    private String matchingPassword;

    @Column
    private String activationCode;

    @Column
    private boolean isActivated;

    @Column
    private String img;

    @Column
    private boolean lockStatus=true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "likedUsers")
    private Set<Topic> likedTopics;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "dislikedUsers")
    private Set<Topic> dislikedTopics;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_themes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "theme_id"))
    private Set<Theme> themes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<PasswordRecoveryToken> recoveryTokens;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "likedUsers")
    private Set<Comment> likedComments;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "dislikedUsers")
    private Set<Comment> dislikedComments;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(getRole());
        return authorities;
    }

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String firstName, String lastName, String username, String password, Role role, boolean isActivated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActivated = isActivated;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public boolean getLockStatus() {
        return lockStatus;
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
        return this.lockStatus;
    }


    /**
     * Переопределенный метод сравнения двух пользователей.
     * В данном случае сравнение производится только по id, т.к. его значение уникально для каждого пользователя.
     *
     * @param obj - пользователь для сравнения с текущим
     * @return - true - если это один и тот же пользователь, иначе - false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) obj;
        return (id == user.getId());
    }
}
