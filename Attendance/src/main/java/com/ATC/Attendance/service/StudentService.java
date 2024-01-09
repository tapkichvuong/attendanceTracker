package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.entities.SubjectEntity;

import com.ATC.Attendance.repository.SubjectRepository;
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

    private final SubjectRepository subjectRepository;


    public StudentService(StudentRepository studentRepository, SessionRepository sessionRepository, AttendanceRepository attendanceRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.sessionRepository = sessionRepository;
        this.attendanceRepository = attendanceRepository;
        this.subjectRepository = subjectRepository;
    }

    public boolean joinSession(Long sessionId, String studentCode, int isTrue) {
        if(isTrue == 0){
            return false;
        }
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

    public static double calculateEuclideanDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public List<SessionResponse> getSessions(String studentCode, Double longitude, Double latitude) {
        List<SessionResponse> results = new ArrayList<>();
        Optional<StudentEntity> student = studentRepository.findById(studentCode);
        if (student.isPresent()) {
            List<SubjectEntity> subjects = subjectRepository.findSubject(student.get().getStudentCode());
            System.out.println("subject size: " + subjects.size());
            List<LessonEntity> lessons = new ArrayList<>();
            for (SubjectEntity subject : subjects) {
                lessons.addAll(subject.getLessons());
            }

            List<SessionEntity> sessions = sessionRepository.findByIsActiveIsTrueAndTimeEndGreaterThanAndLessonIn(LocalDateTime.now(), lessons);
            System.out.println("session size: " + sessions.size());
            for (SessionEntity s : sessions) {
                Double distance = calculateEuclideanDistance(longitude, latitude, s.getLongitude(), s.getLatitude());
                System.out.println(distance);
                SessionResponse sessionResponse = new SessionResponse(s.getSId(),
                        s.getTimeEnd(), s.getTimeStart(), s.isActive(), s.getLesson().getLessonName(), s.getLesson().getSubject().getSubjectName());
                if(distance < 0.0002){
                    results.add(sessionResponse);
                }
                System.out.println("session size: " + sessions.size());
            }
        }
        return results;

    }
}
