package com.Graduation.EduTracker.controller;

import com.Graduation.EduTracker.Dtos.QuizDto;
import com.Graduation.EduTracker.Models.Quiz;
import com.Graduation.EduTracker.Service.Implementation.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quizzes")
@RequiredArgsConstructor
public class QuizResources {

    private final QuizService quizService;

    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        QuizDto quiz = quizService.getQuizById(id);
        if (quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto QuizDto) {
        QuizDto createdQuiz = quizService.createQuiz(QuizDto);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable Long id, @RequestBody QuizDto QuizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(id, QuizDto);
        if (updatedQuiz != null) {
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deleted = quizService.deleteQuiz(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}



