package com.LungCancerDetection.Security.Controller;


import com.LungCancerDetection.Security.Dto.AvailableDatesResponseDto;
import com.LungCancerDetection.Security.Dto.DoctorResponseDto;
import com.LungCancerDetection.Security.Dto.SlotResponseDto;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.TimeSlotEntity;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import com.LungCancerDetection.Security.Repository.TimeSlotRepository;
import com.LungCancerDetection.Security.Service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepo;
    private final DoctorAvailabilityService availabilityService;
    private final TimeSlotRepository slotRepo;

    // 1️⃣ Doctor Info
    @GetMapping("/{id}")
    public DoctorResponseDto getDoctor(@PathVariable Long id) {

        DoctorEntity doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .doctorName(doctor.getName())
                .specialization(doctor.getSpecialization())
                .hospital(doctor.getHospital().getName())
                .consultationFee(doctor.getConsultationFee())
                .build();
    }

    // 2️⃣ Available Dates
    @GetMapping("/{id}/available-dates")
    public AvailableDatesResponseDto getAvailableDates(@PathVariable Long id) {

        List<String> dates = availabilityService.getAvailableDates(id)
                .stream()
                .map(a -> a.getDate().toString())
                .toList();

        return AvailableDatesResponseDto.builder()
                .availableDates(dates)
                .build();
    }

    // 3️⃣ Slots
    @GetMapping("/{id}/slots")
    public SlotResponseDto getSlots(@PathVariable Long id,
                                    @RequestParam LocalDate date) {

        DoctorEntity doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        List<TimeSlotEntity> slots = slotRepo.findByDoctorAndDate(doctor, date);

        List<String> availableSlots = slots.stream()
                .filter(s -> !s.isBooked())
                .map(s -> s.getTime().format(formatter))
                .toList();

        List<String> bookedSlots = slots.stream()
                .filter(TimeSlotEntity::isBooked)
                .map(s -> s.getTime().format(formatter))
                .toList();

        return SlotResponseDto.builder()
                .slots(availableSlots)
                .bookedSlots(bookedSlots)
                .build();
    }
}
