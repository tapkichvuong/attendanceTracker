package com.ATC.Attendance.entities;




import jakarta.persistence.CascadeType;
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
@Table(name="Attendances_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @IdClass(AttendanceId.class)
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "sessionId",
        referencedColumnName = "SId"
    )
    private SessionEntity session;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "studentCode",
        referencedColumnName = "studentCode"
    )
    private StudentEntity student;
}
