package com.LungCancerDetection.Security.Controller;




import com.LungCancerDetection.Security.Service.PaymentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // CREATE ORDER
    @PostMapping("/create-order/{appointmentId}")
    public String createOrder(@PathVariable Long appointmentId) throws Exception {
        return paymentService.createOrder(appointmentId);
    }

    // VERIFY
    @PostMapping("/verify")
    public String verify(@RequestBody PaymentVerifyRequest request) throws Exception {

        paymentService.verifyPayment(
                request.getOrderId(),
                request.getPaymentId(),
                request.getSignature()
        );

        return "Payment successful & appointment confirmed";
    }

    @Data
    static class PaymentVerifyRequest {
        private String orderId;
        private String paymentId;
        private String signature;
    }
}