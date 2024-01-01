package com.ATC.Attendance.controller;

import com.ATC.Attendance.dto.RegisterRequest;
import com.ATC.Attendance.dto.RegisterResponse;
import com.ATC.Attendance.dto.SessionRequest;
import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(path = "/activate")
    public ResponseEntity<SessionResponse> activeSession(@RequestHeader("Authorization") String jwtToken, @RequestBody SessionRequest sessionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.activeSession(jwtToken, sessionRequest));
    }
}
