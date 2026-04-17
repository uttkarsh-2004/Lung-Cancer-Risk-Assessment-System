package com.LungCancerDetection.Security.Service;



import com.LungCancerDetection.Security.Entity.AppointmentEntity;
import com.LungCancerDetection.Security.Enums.PaymentStatus;
import com.LungCancerDetection.Security.Repository.AppointmentRepository;
import com.razorpay.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    private final AppointmentRepository appointmentRepo;
    private final AppointmentService appointmentService;
    private final EmailService emailService;

    // 🔥 CREATE ORDER
    public String createOrder(Long appointmentId) throws Exception {

        AppointmentEntity appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(appointment.getAmount() * 100)); // in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + appointmentId);

        Order order = client.orders.create(options);

        appointment.setRazorpayOrderId(order.get("id"));
        appointmentRepo.save(appointment);

        return order.get("id");
    }

    // 🔥 VERIFY PAYMENT
    @Transactional
    public void verifyPayment(String orderId,
                              String paymentId,
                              String signature) throws Exception {

        String payload = orderId + "|" + paymentId;

        String generatedSignature = hmacSHA256(payload, keySecret);

        if (!generatedSignature.equals(signature)) {
            throw new RuntimeException("Invalid payment signature");
        }

        // 🔥 fetch appointment
        AppointmentEntity appointment = appointmentRepo
                .findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setRazorpayPaymentId(paymentId);
        if (appointment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return; // already processed
        }
        // 🔥 confirm booking
        appointmentService.confirmAppointment(appointment);

        ;
        emailService.sendAppointmentConfirmation(appointment.getPatient().getEmail());



    }
    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
        byte[] rawHmac = mac.doFinal(data.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : rawHmac) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) hex.append('0');
            hex.append(s);
        }
        return hex.toString();
    }

}