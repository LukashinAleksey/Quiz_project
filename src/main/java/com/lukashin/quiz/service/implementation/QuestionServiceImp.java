package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImp implements QuestionService {

    QuestionRepository questionRepository;

    public QuestionServiceImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void addOrUpdateQuestion(QuestionDto questionDto) {
        Question question;
        if (questionDto.getIdQuestion() != null) {
            question = getQuestionById(questionDto.getIdQuestion());
        } else {
            question = new Question();
        }
        if (questionDto.getTextQuestion() != null) {
            question.setTextQuestion(questionDto.getTextQuestion());
        }
        if (questionDto.getRightAnswer() != null) {
            question.setRightAnswer(questionDto.getRightAnswer());
        }
        if (questionDto.getComplexity() != null) {
            question.setComplexity(questionDto.getComplexity());
        }

        questionRepository.save(question);
    }

    @Override
    @Transactional
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional
    public Question getQuestionById(Long idQuestion) {
        return questionRepository.findById(idQuestion).get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Set<Question> getQuestionToGame(Byte numberOfQuestion, Complexity complexity) {
        Set<Question> questions = new HashSet<>();
        List<Question> questionList = questionRepository.findQuestionByComplexity(complexity);
        Collections.shuffle(questionList);
        return questionList.stream().limit(numberOfQuestion).collect(Collectors.toSet());
    }
}
