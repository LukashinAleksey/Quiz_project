package com.lukashin.quiz.controller;

import com.lukashin.quiz.controller.dto.QuestionDto;
import com.lukashin.quiz.model.question.Question;
import com.lukashin.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/quiz/apiQuestion")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/")
    public String addNewQuestion(@RequestBody QuestionDto questionDto){
        questionService.addOrUpdateQuestion(questionDto);
        return "The question is saved";
    }

    @PutMapping("/")
    public String updateQuestion(@RequestBody QuestionDto questionDto){
        questionService.addOrUpdateQuestion(questionDto);
        return "The question is update";
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
    public String deleteQuestionById(@PathVariable Long id){
        questionService.deleteById(id);
        return "Question with id = " + id + " was delete!";
    }
}
