package com.ATC.Attendance.respository;

import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThanAndLessonIn(
            LocalDateTime timeEnd,
            List<LessonEntity> lessons
    );

}
