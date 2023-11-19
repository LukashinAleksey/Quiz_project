package com.lukashin.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long idGame;

    @Column(name = "game_complexity")
    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @Column(name = "number_of_question")
    private byte numberOfQuestion;

    @ManyToMany()
//    @JsonIgnore
    @JoinTable(name = "game_question",
    joinColumns = {@JoinColumn(name = "id_game")},
    inverseJoinColumns = {@JoinColumn(name = "id_question")})
    private List<Question> questionList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_user")
//    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "game",
            cascade = CascadeType.ALL)
    private List<Answer> answers;
}
