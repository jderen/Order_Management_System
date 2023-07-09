package app.application.order.port.in.facade;

import app.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderFacade {

    List<Order> findAllOrders();
    Optional<Order> findOrderById(Long id);
    Order createOrder(Order order);
    Order confirmOrder(Long id);
    Order cancelOrder(Long id);
    Order completeOrder(Long id);
}
