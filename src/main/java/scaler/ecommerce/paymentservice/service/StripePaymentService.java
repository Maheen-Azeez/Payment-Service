package scaler.ecommerce.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentService implements IPaymentService{
    @Value("${stripe.api.key}")
    String stripeApiKey;
    @Override
    public String GeneratePaymentLink(Long orderId, Long amount, String currency) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Product product = createProduct("Product Name");
        Price price = createPrice(product.getId(), amount, currency);

        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build()
                )
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("https://example.com")
                                                .build()
                                )
                                .build()
                )
                .build();

        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl();
    }

    private Product createProduct(String productName) throws StripeException {
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(productName)
                .build();

        return Product.create(productParams);
    }

    private Price createPrice(String productId, Long amount, String currency) throws StripeException {
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(amount)
                .setProduct(productId)
                .build();

        return Price.create(priceParams);
    }
}
