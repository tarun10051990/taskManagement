package com.taskmanagement.demo.service;

import com.taskmanagement.demo.dto.AuthenticationDto;
import com.taskmanagement.demo.dto.RegisterDto;
import com.taskmanagement.demo.entity.User;
import com.taskmanagement.demo.repository.UserRepository;
import com.taskmanagement.demo.response.AuthenticationResponse;
import com.taskmanagement.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDto request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationDto request) {
        log.info(request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        log.info(authentication.getAuthorities().toString());
        if(authentication.isAuthenticated()) {
            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow();
            log.info("her1");
            var jwtToken = jwtService.generateToken(user.getUsername());
            log.info(jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        }
        return null;
    }




    }