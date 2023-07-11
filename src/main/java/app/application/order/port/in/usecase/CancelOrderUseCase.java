package app.application.order.port.in.usecase;

import app.domain.order.Order;

public interface CancelOrderUseCase {

    Order apply(Long id);
}
