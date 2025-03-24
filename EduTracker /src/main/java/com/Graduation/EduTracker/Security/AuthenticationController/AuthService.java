package com.Graduation.EduTracker.Security.AuthenticationController;


import com.Graduation.EduTracker.Models.RoleEnum;
import com.Graduation.EduTracker.Models.User;
import com.Graduation.EduTracker.Repos.RoleRepo;
import com.Graduation.EduTracker.Repos.UserRepo;
import com.Graduation.EduTracker.Security.CustomFilter.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserRepo userRepo;
    private final JwtService jwTservice;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo repo;



     public ResponseEntity<?> register(RegisterRequest registerRequest) throws Exception {
        Optional <User> optional = userRepo.findByUsername(registerRequest.getusername());
         if (optional.isPresent()){
             throw new Exception("user already exists");
         }
         else {
         var user = User.builder()
                 .firstname(registerRequest.getfirstname())
                 .lastname(registerRequest.getLastname())
                 .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                 .username(registerRequest.getusername())
                 .phoneNumber(registerRequest.getPhoneNumber())
                 .roles(List.of(repo.findByName(RoleEnum.valueOf(registerRequest.getRole())).orElseThrow()))
                 .active(true)
                 .build();
            return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
            }
        }

    public AuthenticationResponse authenticate(AuthenticationRequest request)  {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        User user=userRepo.findByUsername(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        var generatedToken=jwTservice.GenerateToken(user);
        var refresh_token = jwTservice.Create_Refresh_Token(user);
        log.info("the Auth token is >> {}" , generatedToken);
        return AuthenticationResponse.builder()
                .access_token(generatedToken)
                .Refresh_token(refresh_token)
                .build();
    }

    public void refresh_token(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        final String requestHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refresh_token;
        final String username;
        if(requestHeader == null || !requestHeader.startsWith("Bearer ")){
            return;
        }
        refresh_token=requestHeader.substring(7);
        username=jwTservice.ExtractUsername(refresh_token);
        if (username != null) {
            UserDetails userDetails = this.userRepo.findByUsername(username).orElseThrow();
            if (jwTservice.valid(refresh_token, userDetails)) {
                var access = jwTservice.GenerateToken(userDetails);
                var auth_response = AuthenticationResponse.builder()
                        .access_token(access)
                        .Refresh_token(refresh_token)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), auth_response);
            }
        }
      }

       }
