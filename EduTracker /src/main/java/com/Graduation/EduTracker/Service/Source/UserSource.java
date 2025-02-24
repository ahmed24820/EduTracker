package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserSource {

    Optional<User> Save(User user);

    void delete (long id);

    Optional<User> Update(User UpdatedOne);

    Optional<User> findByUserName(String UserName);

    Optional<User> findById(long id);

    List<User> getAllUsers();
}
