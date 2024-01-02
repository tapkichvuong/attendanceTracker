package com.ATC.Attendance.controller;


import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<SessionResponse> response = this.studentService.getSessions();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
