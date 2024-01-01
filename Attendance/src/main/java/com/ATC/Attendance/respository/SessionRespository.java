package com.ATC.Attendance.respository;

import com.ATC.Attendance.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRespository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findById(Long id);
}
