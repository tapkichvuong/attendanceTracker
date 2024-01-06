package com.ATC.Attendance.controller;


import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.entities.UserEntity;
import com.ATC.Attendance.service.StudentService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }
    @GetMapping(path = "/sessions")
    public ResponseEntity<List<SessionResponse>> getSessions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        List<SessionResponse> response = this.studentService.getSessions(user.getUserCode());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
