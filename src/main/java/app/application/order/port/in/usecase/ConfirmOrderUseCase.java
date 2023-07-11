package app.application.order.port.in.usecase;

import app.domain.order.Order;

public interface ConfirmOrderUseCase {

    Order apply(Long id);
}
