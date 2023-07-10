package app.application.order.port.out;

import app.domain.order.Order;

import java.util.Optional;

public interface FindOrderPort {

    Optional<Order> find(Long id);
}
