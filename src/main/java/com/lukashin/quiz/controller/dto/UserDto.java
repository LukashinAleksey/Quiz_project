package com.lukashin.quiz.controller.dto;

import com.lukashin.quiz.model.Game;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long idUser;
    private String userName;
    private List<Game> gameList;
}
