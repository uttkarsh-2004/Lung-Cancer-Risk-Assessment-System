package com.LungCancerDetection.Security.Service;


import com.LungCancerDetection.Security.Entity.DoctorAvailabilityEntity;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Repository.DoctorAvailabilityRepository;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorRepository doctorRepo;
    private final SlotGenerationService slotService;

    public void addAvailability(Long doctorId, List<LocalDate> dates) {

        DoctorEntity doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        for (LocalDate date : dates) {


            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) continue;

            if (availabilityRepo.existsByDoctorAndDate(doctor, date)) continue;

            DoctorAvailabilityEntity availability = new DoctorAvailabilityEntity();
            availability.setDoctor(doctor);
            availability.setDate(date);
            availability.setAvailable(true);

            availabilityRepo.save(availability);

            // 🔥 generate slots automatically
            slotService.generateSlots(doctor, date);
        }
    }

    public List<DoctorAvailabilityEntity> getAvailableDates(Long doctorId) {

        DoctorEntity doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return availabilityRepo.findByDoctorAndIsAvailableTrue(doctor);
    }
}