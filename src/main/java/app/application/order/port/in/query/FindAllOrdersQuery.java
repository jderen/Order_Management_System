package app.application.order.port.in.query;

import app.domain.order.Order;

import java.util.List;

public interface FindAllOrdersQuery {

    List<Order> execute();
}
