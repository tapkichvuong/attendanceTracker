package com.ATC.Attendance.dto;

import com.ATC.Attendance.entities.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbsentRes {
    private Long sessionId;
    private LocalDateTime timeStart;
    private List<StudentEntity> absentStudentList;
}
