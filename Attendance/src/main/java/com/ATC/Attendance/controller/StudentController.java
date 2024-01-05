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
        // TODO: lấy student code từ request thay vì hardcode.
        List<SessionResponse> response = this.studentService.getSessions("1");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
