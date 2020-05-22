package ru.javamentor.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User {

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

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Role role;

    @ManyToMany
//    @JoinTable(name = "user_topic", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="topic_id"))
    private Set<Topic> topicCollection;

}
