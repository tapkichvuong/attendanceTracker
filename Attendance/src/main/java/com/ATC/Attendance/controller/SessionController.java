package com.ATC.Attendance.controller;

import com.ATC.Attendance.dto.ActiveSessionReq;
import com.ATC.Attendance.dto.ActiveSessionRes;
import com.ATC.Attendance.dto.TeachingReq;
import com.ATC.Attendance.dto.TeachingRes;
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
    public ResponseEntity<ActiveSessionRes> activeSession(@RequestHeader("Authorization") String jwtToken, @RequestBody ActiveSessionReq sessionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.activeSession(jwtToken, sessionRequest));
    }

    @GetMapping(path = "/teaching")
    public ResponseEntity<List<TeachingRes>> getSessionByTeacher(@RequestHeader("Authorization") String jwtToken, @RequestBody TeachingReq teaching){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.findSessionsByTeacher(jwtToken, teaching));
    }
}
