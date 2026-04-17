package com.LungCancerDetection.Security.Service;




import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Repository.AppointmentRepository;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import com.LungCancerDetection.Security.Repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorDashboardService {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;
    private final TimeSlotRepository slotRepo;

    private DoctorEntity getDoctor(UserEntity user) {
        return doctorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    // 📅 TODAY
    public List<AppointmentEntity> getTodayAppointments(UserEntity user) {
        DoctorEntity doctor = getDoctor(user);
        return appointmentRepo.findByDoctorAndDate(doctor, LocalDate.now());
    }

    // 📊 UPCOMING
    public List<AppointmentEntity> getUpcoming(UserEntity user) {
        DoctorEntity doctor = getDoctor(user);
        return appointmentRepo.findByDoctorAndDateAfter(doctor, LocalDate.now());
    }

    // 📜 HISTORY
    public List<AppointmentEntity> getHistory(UserEntity user) {
        DoctorEntity doctor = getDoctor(user);
        return appointmentRepo.findByDoctorAndDateBefore(doctor, LocalDate.now());
    }

    // ❌ CANCEL
    @Transactional
    public void cancelAppointment(UserEntity user, Long appointmentId) {

        DoctorEntity doctor = getDoctor(user);

        AppointmentEntity appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        appointment.setStatus(com.LungCancerDetection.Security.Enums.AppointmentStatus.CANCELLED);

        // free slot
        slotRepo.findByDoctorAndDate(doctor, appointment.getDate())
                .stream()
                .filter(s -> s.getTime().equals(appointment.getTime()))
                .findFirst()
                .ifPresent(slot -> {
                    slot.setBooked(false);
                    slotRepo.save(slot);
                });

        appointmentRepo.save(appointment);
    }

    // 🔄 RESCHEDULE
    @Transactional
    public void reschedule(UserEntity user,
                           Long appointmentId,
                           LocalDate newDate,
                           java.time.LocalTime newTime) {

        DoctorEntity doctor = getDoctor(user);

        AppointmentEntity appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // free old slot
        slotRepo.findByDoctorAndDate(doctor, appointment.getDate())
                .stream()
                .filter(s -> s.getTime().equals(appointment.getTime()))
                .findFirst()
                .ifPresent(slot -> {
                    slot.setBooked(false);
                    slotRepo.save(slot);
                });

        // book new slot
        slotRepo.findByDoctorAndDate(doctor, newDate)
                .stream()
                .filter(s -> s.getTime().equals(newTime))
                .findFirst()
                .ifPresent(slot -> {
                    if (slot.isBooked()) {
                        throw new RuntimeException("Slot already booked");
                    }
                    slot.setBooked(true);
                    slotRepo.save(slot);
                });

        appointment.setDate(newDate);
        appointment.setTime(newTime);

        appointmentRepo.save(appointment);
    }

    // 💰 UPDATE FEE
    @Transactional
    public void updateFee(UserEntity user, Double fee) {

        DoctorEntity doctor = getDoctor(user);
        doctor.setConsultationFee(fee);
        doctorRepo.save(doctor);
    }
}