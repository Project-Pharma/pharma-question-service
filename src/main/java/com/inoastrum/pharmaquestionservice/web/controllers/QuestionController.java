package com.inoastrum.pharmaquestionservice.web.controllers;

import com.inoastrum.pharmaquestionservice.services.QuestionService;
import com.inoastrum.pharmaquestionservice.web.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Slf4j
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable UUID questionId) {
        return new ResponseEntity<>(questionService.findQuestionDtoById(questionId), HttpStatus.OK);
    }

    @GetMapping("/{pharmacyId}/questions")
    public ResponseEntity<List<QuestionDto>> getQuestionsByPharmacyId(@PathVariable UUID pharmacyId) {
        return new ResponseEntity<>(questionService.findQuestionsByPharmacyId(pharmacyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createNewQuestion(@RequestBody @Validated QuestionDto questionDto) {
        return new ResponseEntity<>(questionService.saveNewQuestion(questionDto).getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestion(@PathVariable UUID questionId, @RequestBody @Validated QuestionDto questionDto) {
        questionService.updateQuestion(questionId, questionDto);
    }

    @DeleteMapping("/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestionById(questionId);
    }
}
