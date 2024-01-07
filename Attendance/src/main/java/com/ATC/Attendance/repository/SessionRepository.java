package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime timeEnd);
    Optional<SessionEntity> findById(Long id);
    List<SessionEntity> findByTeacher_TeacherCode(String teacherCode);
}
