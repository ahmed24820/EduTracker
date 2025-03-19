package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Role;
import com.Graduation.EduTracker.Models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleEnum role);
}
