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
@Table(name = "Subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String subjectCode;
    @Column(nullable = false)
    private String subjectName;
}
