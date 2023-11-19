package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Answer;
import com.lukashin.quiz.model.Complexity;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class GameServiceImpTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    Complexity complexity;

    Question question1 = Question.builder()
            .idQuestion(1l)
            .textQuestion("How are you?")
            .complexity(complexity.Hard)
            .rightAnswer("I'm okay").build();
    Question question2 = Question.builder()
            .idQuestion(2l)
            .textQuestion("What your name?")
            .complexity(complexity.Hard)
            .rightAnswer("Alex").build();

    User user = User.builder().idUser(1l).userName("Alex").build();
    Byte numberOfQuestion = 2;
    Game game = Game.builder().numberOfQuestion(numberOfQuestion).user(user)
            .questionList(Arrays.asList(question1,question2)).complexity(complexity.Hard).build();


    @Test
    void createGame_whenAddNewGame() {
        GameDTO gameDTO = GameDTO.builder().numberOfQuestion(numberOfQuestion)
                .complexity(complexity.Hard).idUser(1l).build();
        given(gameRepository.save(any(Game.class))).willAnswer((invocation)-> invocation.getArgument(0));

        Game game1 = gameService.createGame(gameDTO,user, Arrays.asList(question1,question2));

        assertEquals(game1, game);
    }

    @Test
    void getGameById_whenReturnGameObject() {
        Long idGame = 1l;
        given(gameRepository.findById(idGame)).willReturn(Optional.ofNullable(game));

        assertEquals(gameService.getGameById(idGame), game);
    }

    @Test
    void getGameById_whenReturnEmpty() {
        Long idGame = 1l;
        given(gameRepository.findById(idGame)).willReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            gameService.getGameById(idGame);
        }, "Game with id = " + idGame + " not found");

        assertEquals("Game with id = " + idGame + " not found", exception.getMessage());
    }

    @Test
    void getAllGame() {
        Game game1 = game;
        given(gameRepository.findAll()).willReturn(Arrays.asList(game,game1));

        assertEquals(gameService.getAllGame(),Arrays.asList(game,game1));
    }

    @Test
    void getResultGame() {
        Answer answer = Answer.builder().textAnswer(question1.getRightAnswer()).build();
        Answer answer1 = Answer.builder().textAnswer("Maksim").build();
        Game gameAfter = Game.builder().idGame(game.getIdGame())
                .complexity(game.getComplexity())
                .questionList(game.getQuestionList()).user(game.getUser())
                .answers(Arrays.asList(answer,answer1)).build();
        given(gameRepository.findById(game.getIdGame())).willReturn(Optional.ofNullable(gameAfter));

        assertEquals(gameService.getResultGame(game.getIdGame()), 50);
    }
}