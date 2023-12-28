package com.ATC.Attendance.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="courses_detail_table",uniqueConstraints = {@UniqueConstraint(columnNames = {"class_id", "course_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursesDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "ID"
    )                                          
    private CoursesEntity course;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
                name = "class_id",
                referencedColumnName = "ID"
        )
    private RegClassEntity regClassEntity;
    private List<LocalDateTime> timeStarts;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CourseDetailStudentMap",
            joinColumns = @JoinColumn(
                    name = "course_detail_id",
                    referencedColumnName = "ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "ID"
            )
    )
    private List<StudentsEntity> students;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CourseDetailLecturerMap",
            joinColumns = @JoinColumn(
                    name = "course_detail_id",
                    referencedColumnName = "ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "lecturer_id",
                    referencedColumnName = "ID"
            )
    )
    private List<LecturersEntity> lecturers;
}
