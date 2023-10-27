package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;

import java.util.List;
import java.util.Set;

public interface GameService {
    public void createGame(GameDTO gameDTO, User user, Set<Question> questions);

    public Game getGameById(Long id);

    public void deleteGameById(Long id);

    public List<Game> getAllGame();

//    public void addQuestionInGame();
}
