package com.lukashin.quiz.model.User;

import com.lukashin.quiz.model.Game;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<Game> games = new ArrayList<>();
}
