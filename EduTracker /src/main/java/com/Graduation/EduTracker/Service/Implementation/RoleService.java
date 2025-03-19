package com.Graduation.EduTracker.Service.Implementation;

import com.Graduation.EduTracker.Models.Role;
import com.Graduation.EduTracker.Models.RoleEnum;
import com.Graduation.EduTracker.Repos.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepo repo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      this.loadRoles();
    }

    public void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.Student, RoleEnum.Teacher, RoleEnum.Assistant};
        Map<RoleEnum, String> RoleDescriptionMap = Map.of(
                RoleEnum.Student, "Students for the application",
                RoleEnum.Teacher, "The Administrator for application & the quiz Maker",
                RoleEnum.Assistant, "someOne who helps in the faculty"
        );
        Arrays.stream(roleNames).forEach((role) -> {
            Optional<Role> optionalRole = repo.findByName(role);

            optionalRole.ifPresentOrElse(System.out::println , () -> {
                Role RoleToCreate = new Role();
                RoleToCreate.setRoleName(role);
                RoleToCreate.setDescription(RoleDescriptionMap.get(role));

                repo.save(RoleToCreate);
            });
        });
    }
}