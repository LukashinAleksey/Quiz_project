package com.lukashin.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_answer")
    private Long idAnswer;

    @Column(name = "text_answer")
    private String textAnswer;

    @ManyToOne
    @JoinColumn(name = "id_game")
    @JsonIgnore
    private Game game;
}
