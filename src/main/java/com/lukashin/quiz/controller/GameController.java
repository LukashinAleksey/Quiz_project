package com.lukashin.quiz.controller;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.QuestionService;
import com.lukashin.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private QuestionService questionService;

    @PostMapping("/")
    public String createGame(@RequestBody GameDTO gameDTO){
        gameService.createGame(gameDTO,
                userService.getUserById(gameDTO.getIdUser()),
                questionService.getQuestionToGame(gameDTO.getNumberOfQuestion(),gameDTO.getComplexity()));
        return "The Game is created";
    }

    @GetMapping("/")
    public List<Game> getAllGames() {
        return gameService.getAllGame();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id){
        return gameService.getGameById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteGameById(@PathVariable Long id){
        gameService.deleteGameById(id);
        return "Game with id = " + id + " was delete!";
    }
}
