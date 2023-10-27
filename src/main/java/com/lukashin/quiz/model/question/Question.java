package com.lukashin.quiz.model.question;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idQuestion;

    @Column(name = "text_question")
    private String textQuestion;

    @Column(name = "right_answer")
    private String rightAnswer;

    @Column(name = "complexity")
    private Complexity complexity;
}
