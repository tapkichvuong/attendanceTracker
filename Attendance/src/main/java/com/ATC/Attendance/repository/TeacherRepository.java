package com.ATC.Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ATC.Attendance.entities.TeacherEntity;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, String> {
    TeacherEntity findByTeacherCode(String teacherCode);

}
