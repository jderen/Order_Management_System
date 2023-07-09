package app.application.order.service;

import app.application.order.port.in.usecase.CompleteOrderUseCase;
import app.domain.order.Order;

public class CompleteOrderService implements CompleteOrderUseCase {

    @Override
    public Order apply(Long id) {
        return null;
    }
}
