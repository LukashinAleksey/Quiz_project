package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class GameServiceImp implements GameService {

    private GameRepository gameRepository;

    public GameServiceImp(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void createGame(GameDTO gameDTO, User user, Set<Question> questions) {
        Game game;
        if (gameDTO.getIdGame() != null) {
            game = getGameById(gameDTO.getIdGame());
        } else {
            game = new Game();
        }
        if (gameDTO.getComplexity() != null) {
            game.setComplexity(gameDTO.getComplexity());
        }
        if (gameDTO.getIdUser() != null) {
            game.setUser(user);
        }
        if (gameDTO.getNumberOfQuestion() != 0) {
            game.setNumberOfQuestion(gameDTO.getNumberOfQuestion());
        }
        if (!questions.isEmpty()) {game.getQuestionSet().addAll(questions);}

        gameRepository.save(game);
    }

    @Override
    @Transactional
    public Game getGameById(Long id) {
        return gameRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Game> getAllGame() {
        return gameRepository.findAll();
    }
}
