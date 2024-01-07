package com.ATC.Attendance.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ATC.Attendance.service.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
   

    @PostMapping
    public ResponseEntity<Boolean> joinSession(@RequestParam("sessionId") Long SessionId,@RequestParam("image") MultipartFile file) {
        String studentCode = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if(role.contains("STUDENT")){
            return ResponseEntity.status(HttpStatus.OK).body(studentService.joinSession(SessionId,studentCode,file));
        }
        throw new IllegalStateException("Only students can join sessions");
    
}
}