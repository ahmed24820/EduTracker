package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Models.Quiz;
import com.Graduation.EduTracker.Models.RoleEnum;
import com.Graduation.EduTracker.Models.User;
import com.Graduation.EduTracker.Repos.QuizRepo;
import com.Graduation.EduTracker.Service.Source.QuizSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuizService implements QuizSource {

     private final QuizRepo quizRepo;

    /* we will make check for the user is a teacher
     not then we will save the quiz or throw exception
    */

     @Override
    public Quiz save(Quiz quiz) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRoles().contains(RoleEnum.Teacher)){
        return quizRepo.save(quiz);
        }
        else
        {
            throw new Exception("users can not make quiz it is only for teachers");
        }

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(long id) {

    }
}
