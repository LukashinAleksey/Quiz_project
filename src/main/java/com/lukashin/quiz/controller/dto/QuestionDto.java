package com.lukashin.quiz.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {

    private Long idQuestion;
    private String textQuestion;
    private String rightAnswer;
}
