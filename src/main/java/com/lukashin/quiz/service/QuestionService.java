package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.question.Question;

import java.util.List;
import java.util.Set;


public interface QuestionService {

    public void addOrUpdateQuestion(QuestionDto questionDto);

    public List<Question> getAllQuestion();

    public Question getQuestionById(Long idQuestion);

    public void deleteById(Long id);

    public Set<Question> getQuestionToGame(Byte numberOfQuestion, Complexity complexity);
}
