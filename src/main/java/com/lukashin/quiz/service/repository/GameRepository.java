package com.lukashin.quiz.service.repository;

import com.lukashin.quiz.model.Game;
import com.lukashin.quiz.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
