package com.ATC.Attendance.repository;

import com.ATC.Attendance.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, Long>{
    UserEntity findByUserCode(String userCode);  
} 
