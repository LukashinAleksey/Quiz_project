package com.lukashin.quiz.service;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.controller.dto.UserDto;
import com.lukashin.quiz.model.User.User;

import java.util.List;

public interface UserService {
    public void addNewUser(UserDto userDto);

    public User getUserById(Long id);

    public List<User> getAllUsers();

    void deleteUserById(Long id);
}
