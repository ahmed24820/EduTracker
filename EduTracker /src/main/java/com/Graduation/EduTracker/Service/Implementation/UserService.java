package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Models.User;
import com.Graduation.EduTracker.Repos.UserRepo;
import com.Graduation.EduTracker.Service.Source.UserSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService implements UserSource {

     private final UserRepo userRepo;

    @Override
    public Optional<User> Save(User user) {
        return Optional.of(userRepo.save(user));
    }

    @Override
    public void  delete(long id) {
        Optional<User> user = userRepo.findById(id);
         userRepo.delete(user.orElseThrow());
    }

    @Override
    public Optional<User> Update(User updatedOne) {
        User ExistedUser = userRepo.findById(updatedOne.getId()).orElseThrow();
        ExistedUser.setFirstname(updatedOne.getFirstname());
        ExistedUser.setLastname(updatedOne.getLastname());
        ExistedUser.setPassword(updatedOne.getPassword());
        ExistedUser.setPhoneNumber(updatedOne.getPhoneNumber());
        return Optional.of(userRepo.save(ExistedUser));
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepo.findById(id);
    }
}
