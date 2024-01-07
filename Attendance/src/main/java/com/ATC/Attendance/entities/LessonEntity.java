package com.ATC.Attendance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long LId;
    @Column(nullable = false, unique = true)
    private String lessonName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "subjectCode",
            referencedColumnName = "subjectCode"
    )
    private SubjectEntity subject;

}
