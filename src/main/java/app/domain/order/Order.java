package app.domain.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Order {

    private Long id;
    private Long version;
    @NonNull
    private OrderStatus orderStatus;

    public static Order createNewOrder() {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)
                .build();
    }
}
