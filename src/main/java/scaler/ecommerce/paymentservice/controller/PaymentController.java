package scaler.ecommerce.paymentservice.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scaler.ecommerce.paymentservice.dto.PaymentRequestDto;
import scaler.ecommerce.paymentservice.service.IPaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final IPaymentService paymentService;
    public PaymentController(@Qualifier("stripe") IPaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    public String generatePaymentLink(@RequestBody PaymentRequestDto paymentRequestDto) throws StripeException {
        //validate request body
        return paymentService.GeneratePaymentLink(paymentRequestDto.getOrder_id()
                ,paymentRequestDto.getAmount(),paymentRequestDto.getCurrency());
    }
}
