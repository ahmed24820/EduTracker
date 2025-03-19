package com.Graduation.EduTracker.controller;

import com.Graduation.EduTracker.Models.User;
import com.Graduation.EduTracker.Service.Implementation.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

      @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable("id") long id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/users")
     public ResponseEntity<?> getAllUsers(){
         return ResponseEntity.ok(userService.getAllUsers());
    }

}
