package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.Exeption.ResourceNotFoundException;
import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.model.Answer;
import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.GameService;
import com.lukashin.quiz.service.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImp implements GameService {

    private GameRepository gameRepository;

    public GameServiceImp(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Game createGame(GameDTO gameDTO, User user, List<Question> questions) {
        Game game;
//        if (gameDTO.getIdGame() != null) {
//            Optional<Game> saveGame = gameRepository.findById(gameDTO.getIdGame());
//            if (!saveGame.isPresent()) {
//                throw new ResourceNotFoundException("Game with id = " + gameDTO.getIdGame() + " not found");
//            } else {
//                game = saveGame.get();
//            }
//        } else {
        game = new Game();
//        }
        if (gameDTO.getComplexity() != null) {
            game.setComplexity(gameDTO.getComplexity());
        }
        if (gameDTO.getIdUser() != null) {
            game.setUser(user);
        }
        if (gameDTO.getNumberOfQuestion() != 0) {
            game.setNumberOfQuestion(gameDTO.getNumberOfQuestion());
        }
        if (!questions.isEmpty()) {
            game.getQuestionList().addAll(questions);
        }

        return gameRepository.save(game);
    }

    @Override
    @Transactional
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game with id = " + id + " not found"));
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

    @Override
    public Long getResultGame(Long id) {
        double result = 0;
        Game game;
        Optional<Game> saveGame = gameRepository.findById(id);
        if (!saveGame.isPresent()) {
            throw new ResourceNotFoundException("Game with id = " + id + " not found");
        } else {
            game = saveGame.get();
        }
        List<Question> questions = game.getQuestionList();
        List<Answer> answers = game.getAnswers();

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getRightAnswer().equalsIgnoreCase(answers.get(i).getTextAnswer())) {
                result++;
            }
        }
        return Math.round((result / questions.size()) * 100);
    }

}
