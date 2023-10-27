package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addOrUpdateQuestion(QuestionDto questionDto){
        Question question;
        if (questionDto.getIdQuestion() != null){
            question = getQuestionById(questionDto.getIdQuestion());
        } else {
            question = new Question();
        }
        if (questionDto.getTextQuestion() != null) { question.setTextQuestion(questionDto.getTextQuestion());}
        if (questionDto.getRightAnswer() != null) { question.setRightAnswer(questionDto.getRightAnswer());}
        if (questionDto.getComplexity() != null) {question.setComplexity(questionDto.getComplexity());}

        questionRepository.save(question);
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long idQuestion) {
        return questionRepository.findById(idQuestion).get();
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
