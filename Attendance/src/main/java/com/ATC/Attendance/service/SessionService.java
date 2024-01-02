package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.ActiveSessionReq;
import com.ATC.Attendance.dto.ActiveSessionRes;
import com.ATC.Attendance.dto.TeachingReq;
import com.ATC.Attendance.dto.TeachingRes;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import com.ATC.Attendance.exception.AppException;
import com.ATC.Attendance.respository.SessionRepository;
import com.ATC.Attendance.respository.TeacherRepository;
import com.ATC.Attendance.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final TeacherRepository teacherRepository;
    private final JwtUtil jwtUtil;

    public SessionService(SessionRepository sessionRepository, TeacherRepository teacherRepository, JwtUtil jwtUtil) {
        this.sessionRepository = sessionRepository;
        this.teacherRepository = teacherRepository;
        this.jwtUtil = jwtUtil;
    }


    //INSERT INTO subject (subject_code, subject_name) VALUES ('CT202', 'Nguyen li may hoc');
    //INSERT INTO lesson (id, lesson_name, subject_code) VALUES (1, 'Gioi thieu may hoc', 'CT202');
    //INSERT INTO teacher (teacher_code, teacher_name) VALUES ('12345', 'Nguyen Van A');
    //INSERT INTO session (is_active, id, lesson_id, time_end, time_start, teacher_code)
    //              VALUES (false, 1, 1, '2024-01-01 10:00:00', '2024-01-01 09:00:00', '12345');
    public ActiveSessionRes activeSession(String jwtToken, ActiveSessionReq sessionRequest) {
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
        return new ActiveSessionRes(1L, "The session is active");
    }

    private TeachingRes mapToTeachingResponse(SessionEntity session) {
        return TeachingRes.builder()
                .Id(session.getId())
                .timeStart(session.getTimeStart())
                .timeEnd(session.getTimeEnd())
                .lesson(session.getLesson())
                .teacher(session.getTeacher())
                .build();

    }

    public List<TeachingRes> findSessionsByTeacher(String jwtToken, TeachingReq teaching) {
        TeacherEntity lecturer = teacherRepository.findByTeacherCode(teaching.getTeacherCode());
        if (lecturer == null) {
            throw new AppException(404, "Teacher not exist");
        }
        try {
            jwtUtil.validateToken(jwtToken);
            List<SessionEntity> sessionEntityList = sessionRepository.findByTeacher_TeacherCode(teaching.getTeacherCode());
            return sessionEntityList.stream().map(this::mapToTeachingResponse).toList();

        } catch (Exception e) {
            // Handle JWT validation exception
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }
}
