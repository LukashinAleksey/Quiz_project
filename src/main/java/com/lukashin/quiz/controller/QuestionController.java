package com.lukashin.quiz.controller;

import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/quiz/apiQuestion")
@RequiredArgsConstructor
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Question addNewQuestion(@RequestBody QuestionDto questionDto){
        return questionService.addOrUpdateQuestion(questionDto);
    }

    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody QuestionDto questionDto){
        Question updatedEmployee = questionService.addOrUpdateQuestion(questionDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @GetMapping("/")
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestion();
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id){
        questionService.deleteById(id);
        return new ResponseEntity<>("Question with id = " + id + " was delete!", HttpStatus.OK) ;
    }
}
