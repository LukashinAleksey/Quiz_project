package com.lukashin.quiz.model.question;

import com.lukashin.quiz.model.Complexity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuestion;

    @Column(name = "text_question")
    private String textQuestion;

    @Column(name = "right_answer")
    private String rightAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexity")
    private Complexity complexity;
}
