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
}
