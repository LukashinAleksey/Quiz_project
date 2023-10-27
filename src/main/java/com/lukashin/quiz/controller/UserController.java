package com.lukashin.quiz.controller;

import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.service.UserService;
import com.lukashin.quiz.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/quiz/apiUser")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public String createUser(@RequestBody UserDto userDto){
        userService.addNewUser(userDto);
        return "The user is created";
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return "User with id = " + id + " was delete!";
    }
}
