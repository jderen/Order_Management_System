package app.application.order.port.in.facade.impl;

import app.application.order.port.in.facade.OrderFacade;
import app.domain.order.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderFacadeImpl implements OrderFacade {


    @Override
    public List<Order> findAllOrders() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order confirmOrder(Long id) {
        return null;
    }

    @Override
    public Order cancelOrder(Long id) {
        return null;
    }

    @Override
    public Order completeOrder(Long id) {
        return null;
    }
}
