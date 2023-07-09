package app.application.order.port.in.usecase;

import app.domain.order.Order;

public interface CreateOrderUseCase {

    Order apply(CreateOrderCommand command);
}
