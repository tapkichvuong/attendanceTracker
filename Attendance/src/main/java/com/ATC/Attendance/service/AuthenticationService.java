package com.ATC.Attendance.service;


import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ATC.Attendance.dto.AuthenticationRequest;
import com.ATC.Attendance.dto.AuthenticationResponse;
import com.ATC.Attendance.dto.RegisterRequest;
import com.ATC.Attendance.entities.UserEntity;
import com.ATC.Attendance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service    
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    
    public AuthenticationResponse register(RegisterRequest request){
        Optional<UserEntity> existingUser = repository.findByUserCode(request.getUserCode());
        if(existingUser.isPresent()){
            return AuthenticationResponse.builder().message("user already exist").build();
        }
        var user = UserEntity.builder().userCode(request.getUserCode()).userPassword(passwordEncoder.encode(request.getPassword())).role(request.getRole().toUpperCase()).build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUserCode(),request.getPassword())
        );
    } catch (BadCredentialsException e) {
         return AuthenticationResponse.builder().message("Invalid username or password").build();
    }
    var user = repository.findByUserCode(request.getUserCode()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
}
}
