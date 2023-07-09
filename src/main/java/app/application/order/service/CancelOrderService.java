package app.application.order.service;

import app.application.order.port.in.usecase.CancelOrderUseCase;
import app.domain.order.Order;

public class CancelOrderService implements CancelOrderUseCase {

    @Override
    public Order apply(Long id) {
        return null;
    }
}
