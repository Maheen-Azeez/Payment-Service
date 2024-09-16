package scaler.ecommerce.paymentservice.service;

import com.stripe.exception.StripeException;

public interface IPaymentService {
    public String GeneratePaymentLink(Long order_id, Long amount,String currency) throws StripeException;
}
