package com.ATC.Attendance.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long Id;
    @Column(nullable = false)
    private LocalDateTime timeStart;
    @Column(nullable = false)
    private LocalDateTime timeEnd;
    @Column(nullable = false)
    private boolean isActive;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "lessonId",
            referencedColumnName = "Id"
    )
    private LessonEntity lesson;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "teacherCode",
        referencedColumnName = "teacherCode"
    )
    private TeacherEntity teacher;

}
