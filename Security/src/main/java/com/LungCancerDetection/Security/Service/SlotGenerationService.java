package com.LungCancerDetection.Security.Service;


import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.TimeSlotEntity;
import com.LungCancerDetection.Security.Repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SlotGenerationService {

    private final TimeSlotRepository slotRepository;

    public void generateSlots(DoctorEntity doctor, LocalDate date) {

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end)) {

            TimeSlotEntity slot = new TimeSlotEntity();
            slot.setDoctor(doctor);
            slot.setDate(date);
            slot.setTime(start);
            slot.setBooked(false);

            slotRepository.save(slot);

            start = start.plusMinutes(30);
        }
    }
}

