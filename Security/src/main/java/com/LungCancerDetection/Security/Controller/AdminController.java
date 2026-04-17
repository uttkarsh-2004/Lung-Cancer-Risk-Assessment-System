package com.LungCancerDetection.Security.Controller;

import com.LungCancerDetection.Security.Dto.*;
import com.LungCancerDetection.Security.Entity.QuestionEntity;
import com.LungCancerDetection.Security.Entity.RoleEntity;
import com.LungCancerDetection.Security.Entity.UserEntity;
import com.LungCancerDetection.Security.Enums.RoleType;
import com.LungCancerDetection.Security.Repository.RoleRepository;
import com.LungCancerDetection.Security.Repository.UserRepository;
import com.LungCancerDetection.Security.Service.AdminService;
import com.LungCancerDetection.Security.Service.DoctorService;
import com.LungCancerDetection.Security.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final QuestionService questionService;
    private final DoctorService doctorService;

    @GetMapping("/get/all/questions")
    public List<QuestionResponseDto> getAllQuestions() {

        return questionService.getAllQuestions();

    }

    @PostMapping("/create/question")
    public QuestionEntity createQuestion(@RequestBody CreateQuestionDto dto) {

        return questionService.createQuestion(dto);

    }

    @DeleteMapping("/delete/question/{id}")
    public boolean deleteQuestion(@PathVariable Long id) {

        return questionService.deleteQuestion(id);

    }
    @PostMapping("/make-doctor/{userId}")
    public String makeDoctor(@PathVariable Long userId,
                             @RequestBody MakeDoctorRequestDto dto) {

        adminService.makeDoctor(userId, dto);
        return "User promoted to DOCTOR";
    }
    // ✅ CREATE
    @PostMapping("/create/doctor")
    public DoctorResponseDto createDoctor(@RequestBody MakeDoctorRequestDto dto) {
        return doctorService.createDoctor(dto);
    }

    // ✅ GET ALL
    @GetMapping("/get/all/doctor")
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ✅ UPDATE
    @PatchMapping("/{id}")
    public DoctorResponseDto updateDoctorPartial(
            @PathVariable Long id,
            @RequestBody MakeDoctorRequestDto dto
    ) {
        return doctorService.updateDoctorPartial(id, dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);

    }
    @GetMapping("/patients")
    public List<PatientResponseDto> getAllPatients() {
        return adminService.getAllPatients();
    }


}