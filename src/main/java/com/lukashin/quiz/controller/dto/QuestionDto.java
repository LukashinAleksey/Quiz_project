package com.lukashin.quiz.controller.dto;

import com.lukashin.quiz.model.Complexity;
import lombok.Data;

@Data
public class QuestionDto {

    private Long idQuestion;
    private String textQuestion;
    private String rightAnswer;
    private Complexity complexity;
}
