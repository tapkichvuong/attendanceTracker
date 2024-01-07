package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.StudentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String>{
	//todo
	StudentEntity findByStudentCode(String studentCode);

	@Query(
			nativeQuery = true,
			value = "SELECT * FROM student_table WHERE is_delete = false"
	)
	List<StudentEntity> getAllStudents();

	@Query(value = "SELECT s.student_code, s.student_name, s.student_image_url FROM Student s " +
			"JOIN regSubject rs ON s.student_code = rs.student_code " +
			"WHERE rs.subject_Code = (SELECT l.subject_Code FROM Lesson l JOIN Session ses ON l.id = ses.lesson_id WHERE ses.sid = ?1) " +
			"AND NOT EXISTS (SELECT * FROM Attendances_table a WHERE a.student_Code = s.student_Code AND a.session_Id = ?1)", nativeQuery = true)
	List<StudentEntity> findStudentsNotAttendedSessionAndRegistered(Long sessionId);
	@Query(value = "SELECT s.student_code, s.student_name, s.student_image_url FROM Student s " +
			"JOIN regSubject rs ON s.student_code = rs.student_code " +
			"WHERE rs.subject_Code = (SELECT l.subject_Code FROM Lesson l JOIN Session ses ON l.id = ses.lesson_id WHERE ses.sid = ?1)", nativeQuery = true)
	List<StudentEntity> findStudentInCourse(Long sessionId);
}
