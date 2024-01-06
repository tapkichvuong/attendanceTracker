package com.ATC.Attendance.service;

import org.springframework.stereotype.Service;

import com.ATC.Attendance.dto.LoginRequest;
import com.ATC.Attendance.dto.LoginResponse;
import com.ATC.Attendance.dto.RegisterRequest;
import com.ATC.Attendance.dto.RegisterResponse;
import com.ATC.Attendance.entities.UserEntity;
import com.ATC.Attendance.exception.AppException;
import com.ATC.Attendance.repository.UserRespository;
import com.ATC.Attendance.utils.JwtUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
@Service
public class UserService {

    private final UserRespository userRepository;
    private final JwtUtil jwtUtil;

    Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            16,
            32);

    public UserService(UserRespository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm())){
            throw new AppException(400, "Password and Confirm password must be same");
        }
        if (userRepository.findByUserCode(registerRequest.getUserCode()) != null){
            throw new AppException(409, "User is existed");
        }
        char[] password = registerRequest.getPassword().toCharArray();
        String hash = argon2.hash(3, // Number of iterations
                64 * 1024, // 64mb
                1, // how many parallel threads to use
                password);
        UserEntity newUser = UserEntity.builder()
                .userCode(registerRequest.getUserCode())
                .userPassword(hash)
                .role(registerRequest.isRole())
                .build();
        userRepository.save(newUser);
        return RegisterResponse.builder().message("Register successfully").build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUserCode(loginRequest.getUserCode());
        if (user == null){
            throw new AppException(400, "Username or password invalid");
        }
        if (!argon2.verify(user.getUserPassword(), loginRequest.getPassword().toCharArray())){
            throw new AppException(400, "Username or password invalid");
        }
        System.out.println("hello");
        String token = jwtUtil.generateToken(user.getUserCode(), user.isRole());
        System.out.println("hi");
        return LoginResponse.builder().token(token).role(user.isRole()).build();
    }
}
