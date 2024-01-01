package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionRequest;
import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.respository.SessionRespository;
import com.ATC.Attendance.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {
    private final SessionRespository sessionRepository;
    private final JwtUtil jwtUtil;

    public SessionService(SessionRespository sessionRespository, JwtUtil jwtUtil) {
        this.sessionRepository = sessionRespository;
        this.jwtUtil = jwtUtil;
    }


    //INSERT INTO subject (subject_code, subject_name) VALUES ('CT202', 'Nguyen li may hoc');
    //INSERT INTO lesson (id, lesson_name, subject_code) VALUES (1, 'Gioi thieu may hoc', 'CT202');
    //INSERT INTO teacher (teacher_code, teacher_name) VALUES ('12345', 'Nguyen Van A');
    //INSERT INTO session (is_active, id, lesson_id, time_end, time_start, teacher_code)
    //INSERT INTO session (is_active, id, lesson_id, time_end, time_start, teacher_code)
    //              VALUES (false, 1, 1, '2024-01-01 10:00:00', '2024-01-01 09:00:00', '12345');
    public SessionResponse activeSession(String jwtToken, SessionRequest sessionRequest) {
        try {
            jwtUtil.validateToken(jwtToken);

            // Add any additional JWT validation logic here, e.g., checking roles, issuer, etc.

            // Find the session by ID
            Optional<SessionEntity> optionalSession = sessionRepository.findById(sessionRequest.getId());

            // If the session exists, activate it by setting isActive to true
            optionalSession.ifPresent(session -> {
                session.setActive(true);
                sessionRepository.save(session); // Update the session in the database
            });

        } catch (Exception e) {
            // Handle JWT validation exception
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return new SessionResponse(1L, "The session is active");
    }
}
