package com.lukashin.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.AnswerService;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GameControllerTest {

    @Autowired
    GameController gameController;
    @Autowired
    private MockMvc mockMvc;
    private Complexity complexity;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GameService gameService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private UserService userService;
    @MockBean
    private AnswerService answerService;


    @Test
    void createGame() throws Exception {
        GameDTO gameDTO = GameDTO.builder().numberOfQuestion((byte) 2)
                .complexity(complexity.Hard).idUser(1l).build();
        User user = User.builder().userName("Alex").idUser(1l).build();
        Question question = Question.builder().build();
        Question question2 = Question.builder().build();
        Game game = Game.builder().idGame(1l).numberOfQuestion(gameDTO.getNumberOfQuestion())
                .complexity(gameDTO.getComplexity()).questionList(Arrays.asList(question, question2))
                .user(user).build();

        given(userService.getUserById(user.getIdUser())).willReturn(user);
        given(questionService.getQuestionToGame(gameDTO.getNumberOfQuestion(), gameDTO.getComplexity()))
                .willReturn(Arrays.asList(question, question2));
        given(gameService.createGame(any(GameDTO.class), any(User.class), anyList())).willReturn(game);

        ResultActions response = mockMvc.perform(post("/quiz/apiGame/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)));

        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getAllGames() throws Exception {
        Game game = Game.builder().idGame(1L).complexity(complexity.Hard).build();
        Game game1 = Game.builder().idGame(2L).complexity(complexity.Easy).build();
        List<Game> gameList = new ArrayList<>();
        gameList.addAll(Arrays.asList(game, game1));
        given(gameService.getAllGame()).willReturn(gameList);

        mockMvc.perform(get("/quiz/apiGame/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(gameList.size())))
                .andExpect(jsonPath("$[*].idGame", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].complexity",
                        containsInAnyOrder(game.getComplexity().toString(),
                                game1.getComplexity().toString())));

    }

    @Test
    void getGameById_whenReturnGameObject() throws Exception {
        Game game = Game.builder().idGame(1L).complexity(complexity.Hard).build();
        given(gameService.getGameById(game.getIdGame())).willReturn(game);

        mockMvc.perform(get("/quiz/apiGame/{id}", game.getIdGame()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.idGame", is(game.getIdGame().intValue())))
                .andExpect(jsonPath("$.complexity", is(game.getComplexity().toString())));
    }

    @Test
    void getGameById_whenReturnEmpty() throws Exception {
        Long idGame = 1l;
        given(gameService.getGameById(anyLong()))
                .willThrow(new ResourceNotFoundException("Question with id = " + idGame + " not found"));


        mockMvc.perform(get("/quiz/apiGame/{id}", idGame))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteGameById() throws Exception {
        Long idGame = 1l;
        willDoNothing().given(gameService).deleteGameById(idGame);

        mockMvc.perform(delete("/quiz/apiGame/{id}", idGame))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getResult() throws Exception {
        Long idGame = 1l;
        given(gameService.getResultGame(anyLong())).willReturn(50L);

        ResultActions response = mockMvc.perform(get("/quiz/apiGame/{id}/result", idGame));

        response.andExpect(status().isOk())
                .andDo(print());
    }
}