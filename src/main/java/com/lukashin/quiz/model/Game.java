package com.lukashin.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;

    @Column(name = "game_complexity")
    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @Column(name = "number_of_question")
    private byte numberOfQuestion;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "game_question",
    joinColumns = {@JoinColumn(name = "idGame")},
    inverseJoinColumns = {@JoinColumn(name = "idQuestion")})
    private Set<Question> questionSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;
}
