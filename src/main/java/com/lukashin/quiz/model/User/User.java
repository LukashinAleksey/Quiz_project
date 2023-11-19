package com.lukashin.quiz.model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukashin.quiz.model.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Game> games;


    public void addGameToUser(Game game) {
        if (games == null) {
            games = new ArrayList<>();
        }
        games.add(game);
        game.setUser(this);
    }
}
