package com.LungCancerDetection.Security.Service;



import com.LungCancerDetection.Security.Entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
//    private final UserEntity user;


    public void sendWelcomeEmail(String toEmail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Lung Cancer Detection System");
        message.setText("Welcome Your account has been successfully created on Lung Cancer Detection System.");

        mailSender.send(message);
    }

    public void sendAppointmentConfirmation(String toEmail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Appointment Confirmed");
        message.setText("Your appointment has been successfully booked.");

        mailSender.send(message);
    }
}