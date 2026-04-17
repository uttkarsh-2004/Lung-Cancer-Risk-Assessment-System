package com.LungCancerDetection.Security.Service;


import com.LungCancerDetection.Security.Dto.DoctorResponseDto;
import com.LungCancerDetection.Security.Dto.MakeDoctorRequestDto;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.HospitalEntity;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import com.LungCancerDetection.Security.Repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    // ✅ CREATE
    public DoctorResponseDto createDoctor(MakeDoctorRequestDto dto) {

        HospitalEntity hospital = new HospitalEntity();
        hospital.setName(dto.getHospitalName());

        hospital = hospitalRepository.save(hospital);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setName(dto.getDoctorName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setCity(dto.getCity());
        doctor.setContactNumber(dto.getContactNumber());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setHospital(hospital);
        doctor.setImageUrl(dto.getUrlImage());

        doctor = doctorRepository.save(doctor);

        return mapToDto(doctor);
    }

    // ✅ GET ALL
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ✅ UPDATE
    public DoctorResponseDto updateDoctorPartial(Long id, MakeDoctorRequestDto dto) {

        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (dto.getDoctorName() != null) {
            doctor.setName(dto.getDoctorName());
        }

        if (dto.getSpecialization() != null) {
            doctor.setSpecialization(dto.getSpecialization());
        }

        if (dto.getCity() != null) {
            doctor.setCity(dto.getCity());
        }

        if (dto.getContactNumber() != null) {
            doctor.setContactNumber(dto.getContactNumber());
        }

        if (dto.getConsultationFee() != null) {
            doctor.setConsultationFee(dto.getConsultationFee());
        }

        if (dto.getHospitalName() != null) {
            if (doctor.getHospital() != null) {
                doctor.getHospital().setName(dto.getHospitalName());
            }
        }

        doctor = doctorRepository.save(doctor);

        return mapToDto(doctor);
    }

    // ✅ DELETE
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    // ✅ MAPPER
    private DoctorResponseDto mapToDto(DoctorEntity doctor) {
        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .doctorName(doctor.getName())
                .specialization(doctor.getSpecialization())
                .city(doctor.getCity())
                .contactNumber(doctor.getContactNumber())
                .hospital(doctor.getHospital() != null ? doctor.getHospital().getName() : null)
                .consultationFee(doctor.getConsultationFee())
                .build();
    }
}