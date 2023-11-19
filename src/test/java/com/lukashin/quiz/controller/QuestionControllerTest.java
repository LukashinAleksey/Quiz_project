package com.lukashin.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private QuestionController questionController;

    @Autowired
    private MockMvc mockMvc;

    private Complexity complexity;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService questionService;


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
    void getAllQuestion() throws Exception {
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(Arrays.asList(question1, question2, question3));
        given(questionService.getAllQuestion()).willReturn(questionList);

        mockMvc.perform(get("/quiz/apiQuestion/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(questionList.size())))
                .andExpect(jsonPath("$[*].idQuestion",
                        containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].textQuestion",
                        containsInAnyOrder(question1.getTextQuestion(),
                                question2.getTextQuestion(),
                                question3.getTextQuestion())))
                .andExpect(jsonPath("$[*].complexity",
                        containsInAnyOrder(question1.getComplexity().toString(),
                                question2.getComplexity().toString(),
                                question3.getComplexity().toString())))
                .andExpect(jsonPath("$[*].rightAnswer",
                        containsInAnyOrder(question1.getRightAnswer(),
                                question2.getRightAnswer(),
                                question3.getRightAnswer())));
    }

    @Test
    void getQuestionById_whenReturnQuestionObject() throws Exception {
        given(questionService.getQuestionById(question1.getIdQuestion())).willReturn(question1);


        mockMvc.perform(get("/quiz/apiQuestion/{id}", question1.getIdQuestion()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.textQuestion", is(question1.getTextQuestion())))
                .andExpect(jsonPath("$.complexity", is(question1.getComplexity().toString())))
                .andExpect(jsonPath("$.rightAnswer", is(question1.getRightAnswer())));
    }

    @Test
    void getQuestionById_thenReturnEmpty() throws Exception {
        given(questionService.getQuestionById(anyLong()))
                .willThrow(new ResourceNotFoundException("Question with id = " + question1.getIdQuestion() + " not found"));


        mockMvc.perform(get("/quiz/apiQuestion/{id}", question1.getIdQuestion()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void addNewQuestion() throws Exception {
        QuestionDto questionDto = QuestionDto.builder().textQuestion(question1.getTextQuestion())
                .rightAnswer(question1.getRightAnswer())
                .complexity(question1.getComplexity()).build();
        given(questionService.addOrUpdateQuestion(ArgumentMatchers.any(QuestionDto.class)))
                .willReturn(question1);

        ResultActions response = mockMvc.perform(post("/quiz/apiQuestion/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.textQuestion", is(questionDto.getTextQuestion())))
                .andExpect(jsonPath("$.complexity", is(questionDto.getComplexity().toString())))
                .andExpect(jsonPath("$.rightAnswer", is(questionDto.getRightAnswer())));
    }

    @Test
    void updateQuestion_thenReturnUpdateQuestionObject() throws Exception {
        QuestionDto questionDto = QuestionDto.builder()
                .idQuestion(question1.getIdQuestion())
                .textQuestion(question2.getTextQuestion())
                .rightAnswer(question2.getRightAnswer())
                .complexity(question2.getComplexity()).build();
        Question questionSaved = Question.builder()
                .idQuestion(question1.getIdQuestion())
                .textQuestion(question2.getTextQuestion())
                .rightAnswer(question2.getRightAnswer())
                .complexity(question2.getComplexity()).build();
        given(questionService.addOrUpdateQuestion(ArgumentMatchers.any(QuestionDto.class)))
                .willReturn(questionSaved);

        ResultActions response = mockMvc.perform(post("/quiz/apiQuestion/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.textQuestion", is(questionDto.getTextQuestion())))
                .andExpect(jsonPath("$.complexity", is(questionDto.getComplexity().toString())))
                .andExpect(jsonPath("$.rightAnswer", is(questionDto.getRightAnswer())));

    }

    @Test
    void updateQuestion_thenReturnException() throws Exception {
        QuestionDto questionDto = QuestionDto.builder()
                .idQuestion(10L).build();
        given(questionService.addOrUpdateQuestion(ArgumentMatchers.any(QuestionDto.class)))
                .willThrow(new ResourceNotFoundException("Question with id = " + questionDto.getIdQuestion() + " not found"));

        ResultActions response = mockMvc.perform(post("/quiz/apiQuestion/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)));

        response.andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteQuestionById() throws Exception{
        Long idQuestion = 1L;
        willDoNothing().given(questionService).deleteById(idQuestion);

        ResultActions response = mockMvc.perform(delete("/quiz/apiQuestion/{id}", idQuestion));

        response.andExpect(status().isOk())
                .andDo(print());
    }
}