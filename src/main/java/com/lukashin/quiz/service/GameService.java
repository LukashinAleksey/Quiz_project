package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;

import java.util.List;

public interface GameService {
    public Game createGame(GameDTO gameDTO, User user, List<Question> questions);

    public Game getGameById(Long id);

    public void deleteGameById(Long id);

    public List<Game> getAllGame();

    public Long getResultGame(Long id);
}
