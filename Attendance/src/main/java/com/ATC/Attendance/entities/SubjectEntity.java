package com.ATC.Attendance.entities;

import java.util.List;


import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<LessonEntity> lessons;
}
