package com.lukashin.quiz.controller.dto;

import com.lukashin.quiz.model.question.Complexity;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {

    private Long idQuestion;
    private String textQuestion;
    private String rightAnswer;
    private Complexity complexity;
}
