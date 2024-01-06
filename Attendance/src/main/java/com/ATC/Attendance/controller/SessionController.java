package com.ATC.Attendance.controller;

import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(path = "/activate")
    public ResponseEntity<ActiveSessionRes> activeSession(@RequestBody ActiveSessionReq sessionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.activeSession(sessionRequest));
    }

    @GetMapping(path = "/teaching")
    public ResponseEntity<List<TeachingRes>> getSessionByTeacher(@RequestBody TeachingReq teaching){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.findSessionsByTeacher(teaching));
    }

    @GetMapping("/absent-registered-students")
    public List<AbsentRes> getAbsentRegisteredStudents(@RequestBody AbsentReq absentReq) {
        System.out.println(absentReq.getSubjectCode());
        return sessionService.getAbsentRegisteredStudents(absentReq);
    }
}
