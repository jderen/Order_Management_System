package app.domain.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Order {

    private Long id;
    private Long version;
    @NonNull
    private OrderStatus orderStatus;
    @NonNull
    private LocalDateTime orderDate;
    @NonNull
    private BigDecimal price;
    @NonNull
    private PaymentMethod paymentMethod;
    @NonNull
    private String name;
    private String description;

    public static Order createNewOrder(BigDecimal price, PaymentMethod paymentMethod, String name, String description) {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)
                .orderDate(LocalDateTime.now())
                .price(price)
                .paymentMethod(paymentMethod)
                .name(name)
                .description(description)
                .build();
    }

    public void confirm() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public void complete() {
        this.orderStatus = OrderStatus.COMPLETED;
    }
}
