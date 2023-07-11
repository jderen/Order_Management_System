package app.adapter.in.order;

import app.domain.order.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
class OrderWebTO {

    @NonNull
    private BigDecimal price;
    @NonNull
    private PaymentMethod paymentMethod;
    @NonNull
    private String name;
    private String description;
}
