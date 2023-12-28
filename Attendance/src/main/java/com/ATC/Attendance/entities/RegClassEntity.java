package com.ATC.Attendance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="reg_class_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    @Column(unique = true, nullable = false)
    private String classCode;
        @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "class_id",
            referencedColumnName = "ID"
    )
    private List<CoursesDetailEntity> courseDetails;
}
