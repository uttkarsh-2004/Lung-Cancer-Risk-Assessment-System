package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.TimeSlotEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Enums.AppointmentStatus;
import com.LungCancerDetection.Security.Enums.PaymentStatus;
import com.LungCancerDetection.Security.Repository.AppointmentRepository;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import com.LungCancerDetection.Security.Repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final TimeSlotRepository slotRepo;
    private final DoctorRepository doctorRepo;

    @Transactional
    public AppointmentEntity initiateAppointment(UserEntity patient,
                                                 Long doctorId,
                                                 LocalDate date,
                                                 LocalTime time) {

        DoctorEntity doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 🔥 Check slot
        TimeSlotEntity slot = slotRepo.findByDoctorAndDate(doctor, date)
                .stream()
                .filter(s -> s.getTime().equals(time))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        // create appointment (PENDING)
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPaymentStatus(PaymentStatus.PENDING);
        appointment.setAmount(doctor.getConsultationFee());

        return appointmentRepo.save(appointment);
    }

    @Transactional
    public void confirmAppointment(AppointmentEntity appointment, TimeSlotEntity slot) {

        slot.setBooked(true);
        slotRepo.save(slot);

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.setPaymentStatus(PaymentStatus.SUCCESS);

        appointmentRepo.save(appointment);
    }
}