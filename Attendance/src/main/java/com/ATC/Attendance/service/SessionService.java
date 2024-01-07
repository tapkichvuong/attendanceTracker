package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import com.ATC.Attendance.exception.AppException;
import com.ATC.Attendance.repository.SessionRepository;
import com.ATC.Attendance.repository.StudentRepository;
import com.ATC.Attendance.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }


    //INSERT INTO subject (subject_code, subject_name) VALUES ('CT202', 'Nguyen li may hoc');
    //INSERT INTO lesson (id, lesson_name, subject_code) VALUES (1, 'Gioi thieu may hoc', 'CT202');
    //INSERT INTO teacher (teacher_code, teacher_name) VALUES ('12345', 'Nguyen Van A');
    //INSERT INTO session (is_active, id, lesson_id, time_end, time_start, teacher_code)
    //              VALUES (false, 1, 1, '2024-01-01 10:00:00', '2024-01-01 09:00:00', '12345');
    public ActiveSessionRes activeSession(ActiveSessionReq sessionRequest) {
        try {

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
                .Id(session.getSId())
                .timeStart(session.getTimeStart())
                .timeEnd(session.getTimeEnd())
                .lesson(session.getLesson())
                .teacher(session.getTeacher())
                .build();

    }

    public List<TeachingRes> findSessionsByTeacher(TeachingReq teaching) {
        TeacherEntity lecturer = teacherRepository.findByTeacherCode(teaching.getTeacherCode());
        if (lecturer == null) {
            throw new AppException(404, "Teacher not exist");
        }
        try {
            List<SessionEntity> sessionEntityList = sessionRepository.findByTeacher_TeacherCode(teaching.getTeacherCode());
            return sessionEntityList.stream().map(this::mapToTeachingResponse).toList();

        } catch (Exception e) {
            // Handle JWT validation exception
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }


//        INSERT INTO student (student_code, student_image_url, student_name)
//    VALUES
//      ('S001', 'https://example.com/student1.jpg', 'Alice Smith'),
//      ('S002', 'https://example.com/student2.png', 'Bob Johnson'),
//      ('S003', 'https://example.com/student3.jpeg', 'Charlie Lee'),
//      ('S004', 'https://example.com/student4.ico', 'David Brown'),
//      ('S005', 'https://example.com/student5.gif', 'Emily Jones');

//    INSERT INTO session (is_active, id, lesson_id, time_end, time_start, teacher_code)
//    VALUES
//            (false, 2, 1, '2024-01-02 10:00:00', '2024-01-02 09:00:00', '12345'),
//            (false, 3, 1, '2024-01-03 10:00:00', '2024-01-03 09:00:00', '12345'),
//            (false, 4, 1, '2024-01-04 10:00:00', '2024-01-04 09:00:00', '12345');

//      INSERT INTO regSubject (student_code, subject_code)
//      VALUES
//            ('S001', 'CT202'),
//            ('S002', 'CT202'),
//            ('S003', 'CT202'),
//            ('S004', 'CT202'),
//            ('S005', 'CT202');

//INSERT INTO Attendances_table(student_code, session_id)
//    VALUES
//            ('S001', 1),
//            ('S002', 1),
//            ('S004', 1),
//            ('S002', 2),
//            ('S003', 2),
//            ('S004', 2),
//            ('S001', 3),
//            ('S003', 3),
//            ('S004', 4),
//            ('S001', 4),
//            ('S004', 3),
//            ('S002', 4);
    public List<AbsentRes> getAbsentRegisteredStudents(AbsentReq absentReq) {
        List<AbsentRes> result = new ArrayList<>();
        System.out.println(absentReq.getSubjectCode());
        List<SessionEntity> sessionEntities = sessionRepository.findSessionOfSubject(absentReq.getSubjectCode());
        for(SessionEntity e: sessionEntities){
            List<StudentEntity> studentEntities = studentRepository.findStudentsNotAttendedSessionAndRegistered(e.getSId());
            AbsentRes absentRes = new AbsentRes(e.getSId(), e.getTimeStart(), studentEntities);
            result.add(absentRes);
        }
        return result;
    }
}
