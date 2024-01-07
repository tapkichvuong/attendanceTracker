package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime timeEnd);

    SessionEntity findBySId(Long SId);

    List<SessionEntity> findByTeacher_TeacherCode(String teacherCode);
}