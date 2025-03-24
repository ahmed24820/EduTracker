package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Dtos.AnswerDto;
import com.Graduation.EduTracker.Dtos.QuestionDto;
import com.Graduation.EduTracker.Models.Answer;
import com.Graduation.EduTracker.Models.Question;
import com.Graduation.EduTracker.Models.Quiz;
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
@Log4j2
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionsRepo questionsRepo;
    private final QuizRepo quizRepository;
    
    public List<QuestionDto> getAllQuestions() {
        return questionsRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuestionDto getQuestionById(Long id) {
        Optional<Question> questionOpt = questionsRepo.findById(id);
        return questionOpt.map(this::convertToDTO).orElse(null);
    }

    public List<QuestionDto> getQuestionsByQuizId(Long quizId) {
        return questionsRepo.findByQuizId(quizId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionDto createQuestion(QuestionDto QuestionDto) {
        Question question = convertToEntity(QuestionDto);

        // Set quiz if quizId is provided
        if (QuestionDto.getQuizId() != null) {
            Optional<Quiz> quizOpt = quizRepository.findById(QuestionDto.getQuizId());
            quizOpt.ifPresent(question::setQuiz);
        }

        Question savedQuestion = questionsRepo.save(question);

        // Save answers
        if (QuestionDto.getAnswers() != null && !QuestionDto.getAnswers().isEmpty()) {
            List<Answer> answers = new ArrayList<>();
            for (AnswerDto answerDTO : QuestionDto.getAnswers()) {
                Answer answer = new Answer();
                answer.setText(answerDTO.getText());
                answer.setQuestion(savedQuestion);
                answers.add(answer);
            }
            savedQuestion.setAnswers(answers);
            savedQuestion = questionsRepo.save(savedQuestion);
        }

        return convertToDTO(savedQuestion);
    }

    @Transactional
    public QuestionDto updateQuestion(Long id, QuestionDto QuestionDto) {
        Optional<Question> questionOpt = questionsRepo.findById(id);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            question.setText(QuestionDto.getText());
            question.setCorrectAnswerIndex(QuestionDto.getCorrectAnswerIndex());

            // Update quiz if quizId is changed
            if (QuestionDto.getQuizId() != null) {
                Optional<Quiz> quizOpt = quizRepository.findById(QuestionDto.getQuizId());
                quizOpt.ifPresent(question::setQuiz);
            }

            // Handle answers (using orphanRemoval=true to remove deleted answers)
            if (QuestionDto.getAnswers() != null) {
                // Clear and rebuild the answer collection
                question.getAnswers().clear();

                for (AnswerDto answerDTO : QuestionDto.getAnswers()) {
                    Answer answer = new Answer();
                    if (answerDTO.getId() != null) {
                        answer.setId(answerDTO.getId());
                    }
                    answer.setText(answerDTO.getText());
                    answer.setQuestion(question);
                    question.getAnswers().add(answer);
                }
            }

            Question updatedQuestion = questionsRepo.save(question);
            return convertToDTO(updatedQuestion);
        }
        return null;
    }

    @Transactional
    public boolean deleteQuestion(Long id) {
        if (questionsRepo.existsById(id)) {
            questionsRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public QuestionDto convertToDTO(Question question) {
        QuestionDto QuestionDto = new QuestionDto();
        QuestionDto.setId(question.getId());
        QuestionDto.setText(question.getText());
        QuestionDto.setCorrectAnswerIndex(question.getCorrectAnswerIndex());

        if (question.getQuiz() != null) {
            QuestionDto.setQuizId(question.getQuiz().getId());
        }

        if (question.getAnswers() != null) {
            List<AnswerDto> answerDTOs = question.getAnswers().stream()
                    .map(answer -> {
                        AnswerDto answerDTO = new AnswerDto();
                        answerDTO.setId(answer.getId());
                        answerDTO.setText(answer.getText());
                        return answerDTO;
                    })
                    .collect(Collectors.toList());
            QuestionDto.setAnswers(answerDTOs);
        }

        return QuestionDto;
    }

    public Question convertToEntity(QuestionDto QuestionDto) {
        Question question = new Question();
        question.setId(QuestionDto.getId());
        question.setText(QuestionDto.getText());
        question.setCorrectAnswerIndex(QuestionDto.getCorrectAnswerIndex());
        return question;
    }
}

