package com.ATC.Attendance.controller;

import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    private final SessionService sessionService;

    public TeacherController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(path = "/activate")
    public ResponseEntity<ActiveSessionResponse> activeSession(@RequestBody ActiveSessionRequest sessionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.activeSession(sessionRequest));
    }

    @GetMapping(path = "/sessions")
    public ResponseEntity<List<TeachingResponse>> getSessionByTeacher(){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.findSessionsByTeacher());
    }


    @GetMapping(path = "/absent-registered-students")
    public ResponseEntity<AbsentResponse> getAbsentRegisteredStudents(@RequestBody AbsentRequest absentRequest) {

        System.out.println(absentRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getAbsentRegisteredStudents(absentRequest));
    }

    @GetMapping(path = "/total-students")
    public ResponseEntity<TotalStudentResponse> getTotalOfStudent(@RequestBody TotalStudentRequest absentReq) {
        System.out.println(absentReq.getSessionId());
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.findStudentInCourse(absentReq));
    }
    @GetMapping(path = "/check-active-session")
    public ResponseEntity<ActiveSessionResponse> getStatusOfSession(@RequestBody ActiveSessionRequest activeSessionRequest){
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getStatusActive(activeSessionRequest));
    }
}
