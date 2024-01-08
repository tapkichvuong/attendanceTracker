package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThanAndLessonIn(
            LocalDateTime timeEnd,
            List<LessonEntity> lessons
    );
    SessionEntity findBySId(Long SId);
    @Query(value = "SELECT ses.id, ses.is_active, ses.time_end, ses.time_start, ses.lesson_id, ses.teacher_code FROM session ses " +
    "JOIN lesson l ON l.id = ses.lesson_id " +
    "WHERE l.subject_code = ?1",
    nativeQuery = true)
    List<SessionEntity> findSessionOfSubject(String subjectCode);
    List<SessionEntity> findByTeacher_TeacherCode(String teacherCode);
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime timeEnd);

}
