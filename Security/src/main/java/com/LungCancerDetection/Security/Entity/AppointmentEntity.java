package com.LungCancerDetection.Security.Entity;

import com.LungCancerDetection.Security.Enums.AppointmentStatus;
import com.LungCancerDetection.Security.Enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity patient;

    @ManyToOne
    private DoctorEntity doctor;

    private LocalDate date;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double amount;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}