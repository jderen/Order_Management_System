package app.application.order.port.in.usecase;

import app.domain.order.PaymentMethod;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateOrderCommand {

    @NonNull
    BigDecimal price;
    @NonNull
    PaymentMethod paymentMethod;
    @NonNull
    String name;
    String description;
}
