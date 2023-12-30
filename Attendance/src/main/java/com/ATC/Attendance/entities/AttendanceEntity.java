package com.ATC.Attendance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Attendances_table", uniqueConstraints = @UniqueConstraint(columnNames = {"studentCode", "sessionId"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceEntity {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "studentCode",
        referencedColumnName = "studentCode"
    )
    private StudentEntity student;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "sessionId",
        referencedColumnName = "Id"
    )
    private SessionEntity session;
}
