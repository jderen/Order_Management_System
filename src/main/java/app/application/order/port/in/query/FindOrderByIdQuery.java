package app.application.order.port.in.query;

import app.domain.order.Order;

import java.util.Optional;

public interface FindOrderByIdQuery {

    Optional<Order> execute(Long id);
}
