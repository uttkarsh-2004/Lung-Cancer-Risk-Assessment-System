package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Dto.MakeDoctorRequestDto;
import com.LungCancerDetection.Security.Dto.PatientResponseDto;
import com.LungCancerDetection.Security.Entity.DoctorEntity;
import com.LungCancerDetection.Security.Entity.HospitalEntity;
import com.LungCancerDetection.Security.Entity.RoleEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Enums.RoleType;
import com.LungCancerDetection.Security.Repository.DoctorRepository;
import com.LungCancerDetection.Security.Repository.HospitalRepository;
import com.LungCancerDetection.Security.Repository.RoleRepository;
import com.LungCancerDetection.Security.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;



    @Transactional
    public void makeDoctor(Long userId, MakeDoctorRequestDto dto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Prevent duplicate doctor
        if (doctorRepository.existsByUser(user)) {
            throw new RuntimeException("User is already a doctor");
        }

        RoleEntity doctorRole = roleRepository.findByRole(RoleType.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 🔥 Validate hospital BEFORE changing role
        HospitalEntity hospital = hospitalRepository.findByName(dto.getHospitalName())
                .orElseGet(() -> {
                    HospitalEntity newHospital = new HospitalEntity();
                    newHospital.setName(dto.getHospitalName());
                    newHospital.setCity(dto.getCity());
                    return hospitalRepository.save(newHospital);
                });

        // 🔥 Create DoctorEntity
        DoctorEntity doctor = new DoctorEntity();
        doctor.setUser(user);
        doctor.setName(dto.getDoctorName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setCity(dto.getCity());
        doctor.setContactNumber(dto.getContactNumber());
        doctor.setHospital(hospital);

        doctorRepository.save(doctor);


        user.getRoles().clear();
        user.getRoles().add(doctorRole);

        userRepository.save(user);
    }

    public List<PatientResponseDto> getAllPatients() {

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles()
                        .stream()
                        .anyMatch(role -> role.getRole() == RoleType.ROLE_PATIENT))
                .map(this::mapToDto)
                .toList();
    }

    private PatientResponseDto mapToDto(UserEntity user) {
        return PatientResponseDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

}
