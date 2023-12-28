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
@Table(name = "lecturer_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    @Column(nullable = false)
    private String lecturerName;
    @Column(nullable = false, unique = true)
    private String lecturerCode;
}   
