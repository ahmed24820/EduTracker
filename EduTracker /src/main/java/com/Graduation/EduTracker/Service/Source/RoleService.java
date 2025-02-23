package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> saveRole (Role role);
    Optional<Role> findById (long id);
    Optional<Role> UpdateRoleDesc (Role NewRole);
}
