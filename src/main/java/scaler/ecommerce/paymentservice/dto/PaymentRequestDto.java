package scaler.ecommerce.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private Long order_id;
    private Long amount;
    private String currency;
}
