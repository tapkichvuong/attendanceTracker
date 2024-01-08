package com.ATC.Attendance.repository;





import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ATC.Attendance.dto.SessionResponse;
import com.ATC.Attendance.entities.SessionEntity;



@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
   SessionEntity findBySId(Long SId);

Collection<SessionResponse> findByIsActiveIsTrueAndTimeEndGreaterThan(LocalDateTime now);
    
}
