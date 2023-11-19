package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImp implements QuestionService {

    QuestionRepository questionRepository;

    public QuestionServiceImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public Question addOrUpdateQuestion(QuestionDto questionDto) {
        Question question;
        if (questionDto.getIdQuestion() != null) {
            Optional<Question> saveQuestion = questionRepository.findById(questionDto.getIdQuestion());
            if (!saveQuestion.isPresent()){
                throw new ResourceNotFoundException("Question with id = " + questionDto.getIdQuestion() + " not found");
            } else {
                question = saveQuestion.get();
            }
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

        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional
    public Question getQuestionById(Long idQuestion) {
        return questionRepository.findById(idQuestion)
                .orElseThrow(() -> new ResourceNotFoundException("Question with id = " + idQuestion + " not found"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionToGame(Byte numberOfQuestion, Complexity complexity) {
        List<Question> questionList = questionRepository.findQuestionByComplexity(complexity);
        Collections.shuffle(questionList);
        return questionList.stream().limit(numberOfQuestion).toList();
    }
}
