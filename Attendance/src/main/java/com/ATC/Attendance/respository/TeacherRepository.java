package com.ATC.Attendance.respository;

import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

    TeacherEntity findByTeacherCode(String teacherCode);
}
