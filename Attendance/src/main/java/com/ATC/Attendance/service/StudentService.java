package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.entities.SubjectEntity;
import com.ATC.Attendance.repository.SessionRepository;
import com.ATC.Attendance.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private SessionRepository sessionRepository;
    private StudentRepository studentRepository;
    public StudentService(SessionRepository sessionRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
    }

    public List<SessionResponse> getSessions(String studentCode) {
        Optional<StudentEntity> student = studentRepository.findById(studentCode);
        List<SubjectEntity> subjects = student.get().getSubjects();
        List<LessonEntity> lessons = new ArrayList<>();
        for (SubjectEntity subject : subjects) {
            lessons.addAll(subject.getLessons());
        }

        List<SessionEntity> sessions = sessionRepository.findByIsActiveIsTrueAndTimeEndGreaterThanAndLessonIn(LocalDateTime.now(), lessons);
        List<SessionResponse> results = new ArrayList<>();
        for (SessionEntity s: sessions) {
            SessionResponse sessionResponse = new SessionResponse(s.getId(), s.getTimeEnd(), s.getTimeStart(), s.isActive());
            results.add(sessionResponse);
        }
        return results;
    }
}
