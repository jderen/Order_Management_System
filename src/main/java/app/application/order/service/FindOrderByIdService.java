package app.application.order.service;

import app.application.order.port.in.query.FindOrderByIdQuery;
import app.domain.order.Order;

import java.util.Optional;

public class FindOrderByIdService implements FindOrderByIdQuery {

    @Override
    public Optional<Order> execute(Long id) {
        return Optional.empty();
    }
}
