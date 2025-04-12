package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Dtos.QuizDto;
import com.Graduation.EduTracker.Mapper.QuizMapper;
import com.Graduation.EduTracker.Models.Answer;
import com.Graduation.EduTracker.Models.Question;
import com.Graduation.EduTracker.Models.Quiz;
import com.Graduation.EduTracker.Repos.AnswersRepo;
import com.Graduation.EduTracker.Repos.QuestionsRepo;
import com.Graduation.EduTracker.Repos.QuizRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuizService {

     private final QuizRepo quizRepo;
     private final QuestionsRepo questionsRepo;
     private final QuizMapper mapper;
     private final AnswersRepo answersRepo;
    /* we will make check for the user is a teacher
     not then we will save the quiz or throw exception
    */

     public List<QuizDto> getAllQuizzes() {
          List<Quiz> AllQuizzes=  quizRepo.findAll();
       return   AllQuizzes.stream().map(mapper::map).toList();
     }

    public QuizDto getQuizById(Long id) {
        Optional<Quiz> quizOpt = quizRepo.findById(id);
        return quizOpt.map(mapper::map).orElse(null);
    }
    @Transactional
    public QuizDto createQuiz(Quiz quiz) {
        quizRepo.save(quiz);
        List<Question> questionListForQuiz = new ArrayList<>();
        for (Question question : quiz.getQuestions()){

            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answersRepo.save(answer);
            }
                question.setQuiz(quiz);
            questionListForQuiz.add(question);
            questionsRepo.save(question);

            }

        return mapper.map(quiz);
    }

    public QuizDto updateQuiz(Long id, QuizDto quizDTO) {
        if (quizRepo.existsById(id)) {
            Quiz quiz = convertToEntity(quizDTO);
            quiz.setId(id);
            Quiz updatedQuiz = quizRepo.save(quiz);
            return convertToDTO(updatedQuiz);
        }
        return null;
    }

    public boolean deleteQuiz(Long id) {
        if (quizRepo.existsById(id)) {
            quizRepo.deleteById(id);
            return true;
        }
        return false;
    }
    public Question addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        question.setQuiz(quiz);
        return questionsRepo.save(question);
     }

    public QuizDto convertToDTO(Quiz quiz) {
        QuizDto quizDTO = new QuizDto();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setDescription(quiz.getDescription());
        // We might need to load questions separately to avoid infinite recursion
        return quizDTO;
    }

    private Quiz convertToEntity(QuizDto quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        // We'd need to handle questions conversion separately
        return quiz;
    }
}
