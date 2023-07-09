package app.application.order.port.in.usecase;

import app.domain.order.Order;

public interface CompleteOrderUseCase {

    Order apply(Long id);
}
