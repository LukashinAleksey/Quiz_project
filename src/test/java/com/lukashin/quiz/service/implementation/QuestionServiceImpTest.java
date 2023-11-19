package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuestionServiceImpTest {

    @Autowired
    private QuestionService questionService;


    @MockBean
    QuestionRepository questionRepository;

    Complexity complexity;


    Question question1 = Question.builder()
            .idQuestion(1l)
            .textQuestion("How are you?")
            .complexity(complexity.Hard)
            .rightAnswer("I'm okay").build();
    Question question2 = Question.builder()
            .idQuestion(2l)
            .textQuestion("What your name?")
            .complexity(complexity.Easy)
            .rightAnswer("Alex").build();

    Question question3 = Question.builder()
            .idQuestion(3l)
            .textQuestion("What your name?")
            .complexity(complexity.Hard)
            .rightAnswer("Maxim").build();


    @Test
    void getAllQuestion() {
        given(questionRepository.findAll()).willReturn(Arrays.asList(question1, question2, question3));

        assertEquals(questionService.getAllQuestion(), Arrays.asList(question1, question2, question3));
    }

    @Test
    void getQuestionById_whenReturnQuestionObject() {
        Long idQuestion = 1l;
        given(questionRepository.findById(idQuestion)).willReturn(Optional.ofNullable(question1));

        assertEquals(questionService.getQuestionById(idQuestion), question1);
    }

    @Test
    void getQuestionById_thenReturnEmpty() {
        Long idQuestion = 1l;
        given(questionRepository.findById(idQuestion)).willReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            questionService.getQuestionById(idQuestion);
        }, "Question with id = " + idQuestion + " not found");

        assertEquals("Question with id = " + idQuestion + " not found", exception.getMessage());

    }

    @Test
    public void getQuestionToGame() {
        when(questionRepository.findQuestionByComplexity(complexity.Hard)).thenReturn(Arrays.asList(question1, question3));

        assertEquals(questionService.getQuestionToGame((byte) 2, Complexity.Hard)
                        .stream().sorted((q, q1) -> (int) (q.getIdQuestion() - q1.getIdQuestion())).toList(),
                Arrays.asList(question1, question3));
    }

    @Test
    void addOrUpdateQuestion_addNewQuestion() {
        QuestionDto questionSaved = QuestionDto.builder()
                .textQuestion(question1.getTextQuestion())
                .rightAnswer(question1.getRightAnswer())
                .complexity(question1.getComplexity()).build();
        question1.setIdQuestion(null);
        given(questionRepository.save(any(Question.class))).willAnswer((invocation)-> invocation.getArgument(0));;

        assertEquals(questionService.addOrUpdateQuestion(questionSaved), question1);
    }

    @Test
    void addOrUpdateQuestion_updateQuestion() {
        QuestionDto questionUpdate = QuestionDto.builder()
                .idQuestion(question2.getIdQuestion())
                .textQuestion(question1.getTextQuestion())
                .rightAnswer(question1.getRightAnswer())
                .complexity(question1.getComplexity()).build();
        Question questionBeforeUpdate = question2;
        Question questionAfterUpdate = Question.builder()
                .idQuestion(question2.getIdQuestion())
                .textQuestion(question1.getTextQuestion())
                .rightAnswer(question1.getRightAnswer())
                .complexity(question1.getComplexity()).build();
        given(questionRepository.save(questionAfterUpdate)).willReturn(questionAfterUpdate);
        given(questionRepository.findById(question2.getIdQuestion())).willReturn(Optional.of(questionBeforeUpdate));

        assertEquals(questionService.addOrUpdateQuestion(questionUpdate), questionAfterUpdate);
    }

    @Test
    void addOrUpdateQuestion_updateQuestionNotFound() {
        Long idQuestion = 1l;
        QuestionDto questionDto = QuestionDto.builder().idQuestion(idQuestion).build();
        given(questionRepository.findById(idQuestion)).willReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            questionService.addOrUpdateQuestion(questionDto);
        }, "Question with id = " + idQuestion + " not found");

        assertEquals("Question with id = " + idQuestion + " not found", exception.getMessage());
    }
}