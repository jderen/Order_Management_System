package app.application.order.service;

import app.application.order.port.in.usecase.ConfirmOrderUseCase;
import app.domain.order.Order;

public class ConfirmOrderService implements ConfirmOrderUseCase {

    @Override
    public Order apply(Long id) {
        return null;
    }
}
