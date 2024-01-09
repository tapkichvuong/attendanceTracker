package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, String> {
    @Query(value = "SELECT sub.subject_code, sub.subject_name FROM Subject sub " +
            "JOIN regSubject rs ON sub.subject_Code = rs.subject_Code " +
            "WHERE rs.student_code = ?1", nativeQuery = true)
    List<SubjectEntity> findSubject(String student_Code);
}
