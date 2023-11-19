package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.controller.dto.AnswerDto;
import com.lukashin.quiz.model.Answer;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.service.AnswerService;
import com.lukashin.quiz.service.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImp implements AnswerService {

    AnswerRepository answerRepository;

    public AnswerServiceImp(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void addNewAnswer(AnswerDto answerDto, Game game) {
        Answer answer = new Answer();
        if (answerDto.getTextAnswer() != null){answer.setTextAnswer(answerDto.getTextAnswer());}
        answer.setGame(game);
        answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }
}
