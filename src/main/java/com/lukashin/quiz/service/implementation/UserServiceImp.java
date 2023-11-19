package com.lukashin.quiz.service.implementation;

import com.lukashin.quiz.controller.dto.GameDTO;
import com.lukashin.quiz.controller.dto.UserDto;
import com.lukashin.quiz.model.User.User;
import com.lukashin.quiz.service.UserService;
import com.lukashin.quiz.service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    @Transactional
    public void addNewUser(UserDto userDto) {
        User user;
        if (userDto.getIdUser() != null){
            user = getUserById(userDto.getIdUser());
        } else {
            user = new User();
        }
        if (userDto.getUserName() != null){user.setUserName(userDto.getUserName());}

        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
