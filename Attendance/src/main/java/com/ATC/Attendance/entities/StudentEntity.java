package com.ATC.Attendance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String studentCode;
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false)
    private String studentImageUrl;
}
