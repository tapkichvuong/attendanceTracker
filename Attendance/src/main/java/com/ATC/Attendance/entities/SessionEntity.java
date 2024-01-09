package com.ATC.Attendance.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionEntity {
    @Id
    @Column(nullable = false, unique = true)
    private Long SId;
    @Column(nullable = false, unique = true)
    private LocalDateTime timeStart;
    @Column(nullable = false)
    private LocalDateTime timeEnd;
    @Column(nullable = false)
    private boolean isActive;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "lessonId",
            referencedColumnName = "LId"
    )
    private LessonEntity lesson;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "teacherCode",
        referencedColumnName = "teacherCode"
    )
    private TeacherEntity teacher;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;
}
