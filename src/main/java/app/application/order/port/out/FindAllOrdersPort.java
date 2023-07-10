package app.application.order.port.out;

import app.domain.order.Order;

import java.util.List;

public interface FindAllOrdersPort {

    List<Order> find();
}
