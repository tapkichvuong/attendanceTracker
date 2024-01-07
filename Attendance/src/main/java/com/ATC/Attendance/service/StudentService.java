package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ATC.Attendance.entities.AttendanceEntity;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.repository.AttendanceRepository;
import com.ATC.Attendance.repository.SessionRepository;
import com.ATC.Attendance.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

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
    

    public List<SessionResponse> getSessions() {
        return sessionRepository.findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime.now()).stream()
                .map(e -> SessionResponse.builder()
                        .Id(e.getSId())
                        .timeEnd(e.getTimeEnd())
                        .timeStart(e.getTimeStart())
                        .isActive(e.isActive())
                        .build())
                .toList();
    }
}
