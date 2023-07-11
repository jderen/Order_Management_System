package app.application.order.port.in.facade.impl;

import app.application.order.port.in.facade.OrderFacade;
import app.application.order.port.in.query.FindAllOrdersQuery;
import app.application.order.port.in.query.FindOrderByIdQuery;
import app.application.order.port.in.usecase.CancelOrderUseCase;
import app.application.order.port.in.usecase.CompleteOrderUseCase;
import app.application.order.port.in.usecase.ConfirmOrderUseCase;
import app.application.order.port.in.usecase.CreateOrderCommand;
import app.application.order.port.in.usecase.CreateOrderUseCase;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final FindAllOrdersQuery findAllOrdersQuery;
    private final FindOrderByIdQuery findOrderByIdQuery;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final CompleteOrderUseCase completeOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final CreateOrderUseCase createOrderUseCase;

    @Override
    public List<Order> findAllOrders() {
        return findAllOrdersQuery.execute();
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return findOrderByIdQuery.execute(id);
    }

    @Override
    public Order createOrder(CreateOrderCommand command) {
        return createOrderUseCase.apply(command);
    }

    @Override
    public Order confirmOrder(Long id) {
        return confirmOrderUseCase.apply(id);
    }

    @Override
    public Order cancelOrder(Long id) {
        return cancelOrderUseCase.apply(id);
    }

    @Override
    public Order completeOrder(Long id) {
        return completeOrderUseCase.apply(id);
    }
}
