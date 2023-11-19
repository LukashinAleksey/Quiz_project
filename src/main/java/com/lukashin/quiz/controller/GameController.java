package com.lukashin.quiz.controller;

import com.lukashin.quiz.controller.dto.AnswerDto;
import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.service.AnswerService;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/quiz/apiGame")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody GameDTO gameDTO) {
        return gameService.createGame(gameDTO,
                userService.getUserById(gameDTO.getIdUser()),
                questionService.getQuestionToGame(gameDTO.getNumberOfQuestion(), gameDTO.getComplexity()));
    }

    @GetMapping("/")
    public List<Game> getAllGames() {
        return gameService.getAllGame();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>("Game with id = " + id + " was delete!", HttpStatus.OK);
    }

    // TODO: 18.11.2023 Возможно лучше будет добавлять сразу лист вопросов
    @PostMapping("/{id}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAnswer(@RequestBody AnswerDto answerDto, @PathVariable Long id) {
        Game game = gameService.getGameById(id);
        answerService.addNewAnswer(answerDto, game);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<String> getResult(@PathVariable Long id) {
        return new ResponseEntity<>("Result = " + gameService.getResultGame(id) + "% right answer", HttpStatus.OK);
    }
}
