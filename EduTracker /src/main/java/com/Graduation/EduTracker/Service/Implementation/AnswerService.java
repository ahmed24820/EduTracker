package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Models.Answer;
import com.Graduation.EduTracker.Models.Question;
import com.Graduation.EduTracker.Repos.AnswersRepo;
import com.Graduation.EduTracker.Repos.QuestionsRepo;
import com.Graduation.EduTracker.Service.Source.AnswerSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AnswerService implements AnswerSource {

    private final AnswersRepo answersRepo;
    private final QuestionsRepo questionsRepo;

    @Override
    public Answer addAnswer(Answer answer) {
        return answersRepo.save(answer);
    }

    @Override
    public void delete(long id) {
        answersRepo.delete(answersRepo.findById(id).orElseThrow());
    }

    @Override
    public void update(long id) {

    }

    @Override
    public Optional<Answer> findById(long id) {
        return answersRepo.findById(id);
    }

    public Answer addAnswerToQuestion(Long questionId, Answer answer) {
        Question question = questionsRepo.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        answer.setQuestion(question);
        return answersRepo.save(answer);
    }
}