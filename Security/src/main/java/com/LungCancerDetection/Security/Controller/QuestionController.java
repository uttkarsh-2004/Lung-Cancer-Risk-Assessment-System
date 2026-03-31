package com.LungCancerDetection.Security.Controller;

import com.LungCancerDetection.Security.Dto.CreateQuestionDto;
import com.LungCancerDetection.Security.Dto.QuestionResponseDto;
import com.LungCancerDetection.Security.Entity.QuestionEntity;
import com.LungCancerDetection.Security.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public List<QuestionResponseDto> getAllQuestions() {

        return questionService.getAllQuestions();

    }

    @PostMapping("/admin")
    public QuestionEntity createQuestion(@RequestBody CreateQuestionDto dto) {

        return questionService.createQuestion(dto);

    }

    @DeleteMapping("/admin/{id}")
    public void deleteQuestion(@PathVariable Long id) {

        questionService.deleteQuestion(id);

    }

}