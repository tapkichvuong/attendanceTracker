package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private SessionRepository sessionRepository;

    public StudentService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionResponse> getSessions() {
        return sessionRepository.findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime.now()).stream()
                .map(e -> SessionResponse.builder()
                        .Id(e.getId())
                        .timeEnd(e.getTimeEnd())
                        .timeStart(e.getTimeStart())
                        .isActive(e.isActive())
                        .build())
                .toList();
    }
}
