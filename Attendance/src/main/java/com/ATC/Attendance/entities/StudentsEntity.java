package com.ATC.Attendance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="student_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private int ID;
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false, unique = true)
    private String studentCode;
}
