package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.entities.SubjectEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ATC.Attendance.entities.AttendanceEntity;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.repository.AttendanceRepository;
import com.ATC.Attendance.repository.SessionRepository;
import com.ATC.Attendance.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;


    public StudentService(StudentRepository studentRepository, SessionRepository sessionRepository, AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.sessionRepository = sessionRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public boolean joinSession(Long sessionId, String studentCode, MultipartFile file) {
        StudentEntity existStudent = studentRepository.findByStudentCode(studentCode);
        SessionEntity existSession = sessionRepository.findBySId(sessionId);
        if(existStudent!=null && existSession!=null){
            AttendanceEntity existAttendance = attendanceRepository.getCourseDetailByClassCodeAndCourseCode(studentCode, sessionId);
            if(existAttendance!=null){
                return true;
            }
            else{
                existAttendance = new AttendanceEntity();
                existAttendance.setStudent(existStudent);
                existAttendance.setSession(existSession);
                attendanceRepository.save(existAttendance);
                return true;
            }
        }
        else{
            return false;
        }
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
            SessionResponse sessionResponse = new SessionResponse(s.getSId(), s.getTimeEnd(), s.getTimeStart(), s.isActive());
            results.add(sessionResponse);
        }
        return results;
    }
}
