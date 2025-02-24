package com.Graduation.EduTracker.Email.Repo;

import com.Graduation.EduTracker.Email.Utils.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.Optional;

@Repository
public interface EmailRepo extends JpaRepository<EmailConfirmation ,Long> {
  Optional<EmailConfirmation> findByToken(String token);
}
