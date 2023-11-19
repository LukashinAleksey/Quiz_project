package com.lukashin.quiz.controller.dto;

import com.lukashin.quiz.model.Complexity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDto {

    private Long idQuestion;
    private String textQuestion;
    private String rightAnswer;
    private Complexity complexity;
}
