package com.ATC.Attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingResponse {
    private Long Id;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private boolean isActive;
    private String lessonName;
    private String subjectName;
}
