package com.ATC.Attendance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ATC.Attendance.entities.AttendanceEntity;





@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
        @Query(
            nativeQuery = true,
            value = """
                    select T1.*
                    from Attendances_table as T1
                    where T1.student_code = ?1 and T1.session_id = ?2
                    """
    )
    AttendanceEntity getCourseDetailByClassCodeAndCourseCode(String studentCode, Long sessionId);
}