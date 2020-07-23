package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс для сохранения ключей восстановления пароля
 *
 * @author Java Mentor
 * @version 1.0
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "password_recovery_token")
public class PasswordRecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String hashEmail;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy в HH:mm")
    private LocalDateTime startTime;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordRecoveryToken(User user) {
        this.user = user;
    }

    public PasswordRecoveryToken(String hashEmail, User user) {
        this.hashEmail = hashEmail;
        this.user = user;
    }


}
