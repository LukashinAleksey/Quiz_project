package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.AnswerDto;
import com.lukashin.quiz.model.Answer;
import com.lukashin.quiz.model.Game;

import java.util.List;

public interface AnswerService {
    public void addNewAnswer(AnswerDto answerDto, Game game);
    public List<Answer> getAllAnswers();
}

