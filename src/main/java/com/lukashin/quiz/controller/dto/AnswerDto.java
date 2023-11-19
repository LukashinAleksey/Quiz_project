package com.lukashin.quiz.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukashin.quiz.model.Game;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AnswerDto {
    private Long idAnswer;
    private String textAnswer;
    private Game game;
}
