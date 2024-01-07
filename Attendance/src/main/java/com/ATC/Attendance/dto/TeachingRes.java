package com.ATC.Attendance.dto;

import com.ATC.Attendance.entities.LessonEntity;
import com.ATC.Attendance.entities.SessionEntity;
import com.ATC.Attendance.entities.TeacherEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingRes {
    private Long Id;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private boolean isActive;
    private LessonEntity lesson;
    private TeacherEntity teacher;
}
