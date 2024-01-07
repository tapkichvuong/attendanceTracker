package com.ATC.Attendance.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String teacherCode;
    @Column(nullable = false)
    private String teacherName;
}
