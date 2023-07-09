package app.application.order.service;

import app.application.order.port.in.usecase.CreateOrderCommand;
import app.application.order.port.in.usecase.CreateOrderUseCase;
import app.domain.order.Order;

public class CreateOrderService implements CreateOrderUseCase {

    @Override
    public Order apply(CreateOrderCommand command) {
        return null;
    }
}
