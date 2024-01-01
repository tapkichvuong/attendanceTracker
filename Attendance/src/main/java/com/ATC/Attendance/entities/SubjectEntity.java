package com.ATC.Attendance.entities;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "RegSubject",
        joinColumns = @JoinColumn(
            name = "subjectCode",
            referencedColumnName = "subjectCode"
        )
        ,
        inverseJoinColumns = @JoinColumn(  
            name = "studentCode",
            referencedColumnName = "studentCode"
        )
    )
    private List<StudentEntity> students;
}
