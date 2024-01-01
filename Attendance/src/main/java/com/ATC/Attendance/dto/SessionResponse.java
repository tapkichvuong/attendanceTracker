package com.ATC.Attendance.dto;

import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponse {
    private Long Id;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private boolean isActive;
}
