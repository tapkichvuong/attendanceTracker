package com.ATC.Attendance.service;

import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.StudentEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import com.ATC.Attendance.exception.AppException;
import com.ATC.Attendance.repository.SessionRepository;
import com.ATC.Attendance.repository.StudentRepository;
import com.ATC.Attendance.repository.TeacherRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


//    INSERT INTO subject (subject_code, subject_name) VALUES ('CT202', 'Nguyen li may hoc');
//    INSERT INTO lesson (lid, lesson_name, subject_code) VALUES (1, 'Gioi thieu may hoc', 'CT202');
//    INSERT INTO teacher (teacher_code, teacher_name) VALUES ('12345', 'Nguyen Van A');

    public ActiveSessionResponse activeSession(Long id) {
        try {

            // Find the session by ID
            Optional<SessionEntity> optionalSession = sessionRepository.findById(id);

            // If the session exists, activate it by setting isActive to true
            optionalSession.ifPresent(session -> {
                if(session.isActive()){
                    session.setActive(false);
                }else{
                    session.setActive(true);
                }
                sessionRepository.save(session); // Update the session in the database
            });
            return new ActiveSessionResponse(optionalSession.get().isActive(), "The status of session is successfully changed");
        } catch (Exception e) {
            // Handle JWT validation exception
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private TeachingResponse mapToTeachingResponse(SessionEntity session) {
        return TeachingResponse.builder()
                .Id(session.getSId())
                .timeStart(session.getTimeStart())
                .timeEnd(session.getTimeEnd())
                .lessonName(session.getLesson().getLessonName())
                .subjectName(session.getLesson().getSubject().getSubjectName())
                .build();
    }

    public List<TeachingResponse> findSessionsByTeacher() {
        String teacherCode = SecurityContextHolder.getContext().getAuthentication().getName();
        TeacherEntity lecturer = teacherRepository.findByTeacherCode(teacherCode);
        if (lecturer == null) {
            throw new AppException(404, "Teacher not exist");
        }
        try {
            List<SessionEntity> sessionEntityList = sessionRepository.findByTeacher_TeacherCode(teacherCode);
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
//
//    INSERT INTO session (is_active, sid, lesson_id, time_end, time_start, teacher_code, longitude, latitude)
//    VALUES
//            (false, 1, 1, '2024-01-01 10:00:00', '2024-01-01 09:00:00', '12345', 10.80888577228034, 106.7133551644191),
//            (false, 2, 1, '2024-01-02 10:00:00', '2024-01-02 09:00:00', '12345', 10.80888577228034, 106.7133551644191),
//            (false, 3, 1, '2024-01-03 10:00:00', '2024-01-03 09:00:00', '12345', 10.80888577228034, 106.7133551644191),
//            (false, 4, 1, '2024-01-04 10:00:00', '2024-01-04 09:00:00', '12345', 10.80888577228034, 106.7133551644191);
//
    //CREATE TABLE RegSubject (
//    subjectCode VARCHAR(255) NOT NULL,
//    studentCode VARCHAR(255) NOT NULL,
//    PRIMARY KEY (subject_Code, student_Code),
//    CONSTRAINT fk_regsubject_subject
//        FOREIGN KEY (subject_Code)
//        REFERENCES Subject(subjectCode),
//    CONSTRAINT fk_regsubject_student
//        FOREIGN KEY (student_Code)
//        REFERENCES Student(student_Code)
//);

//      INSERT INTO regSubject (student_code, subject_code)
//      VALUES
//            ('S001', 'CT202'),
//            ('S002', 'CT202'),
//            ('S003', 'CT202'),
//            ('S004', 'CT202'),
//            ('S005', 'CT202');
//
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

    public AbsentResponse getAbsentRegisteredStudents(Long sessionId) {
        List<StudentEntity> studentEntities =  studentRepository.findStudentsNotAttendedSessionAndRegistered(sessionId);
        List<String> studentCodes = studentEntities.stream()
                .map(StudentEntity::getStudentCode)
                .collect(Collectors.toList());
        return AbsentResponse.builder().studentCode(studentCodes).build();
    }

    public TotalStudentResponse findStudentInCourse(Long sessionId) {
        int count;
        List<StudentEntity> studentEntities = studentRepository.findStudentInCourse(sessionId);
        count = studentEntities.size();
        List<String> studentCodes = studentEntities.stream()
                .map(StudentEntity::getStudentCode)
                .collect(Collectors.toList());
        return TotalStudentResponse.builder()
                .countTotalStudent(count)
                .studentCode(studentCodes)
                .build();
    }

    public ActiveSessionResponse getStatusActive(Long sessionId){
        SessionEntity session = sessionRepository.findBySId(sessionId);
        if(session.isActive()) {
            return ActiveSessionResponse.builder().status(session.isActive()).message("This session is activated").build();
        }
        return ActiveSessionResponse.builder().status(session.isActive()).message("This session is unactivated").build();
    }
}
