package com.LungCancerDetection.Security.Entity;

import com.LungCancerDetection.Security.Entity.DoctorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "date", "time"})
)
public class TimeSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DoctorEntity doctor;

    private LocalDate date;

    private LocalTime time;

    private boolean isBooked = false;
}
