package com.ATC.Attendance.respository;

import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime timeEnd);

}
