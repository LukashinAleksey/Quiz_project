package com.lukashin.quiz.controller.dto;

import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import lombok.Data;

import java.util.Set;

@Data
public class GameDTO {
    private Long idGame;
    private Complexity complexity;
    private byte numberOfQuestion;
    private Set<Question> questionSet;
    private Long idUser;
}
