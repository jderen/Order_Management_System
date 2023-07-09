package app.application.order.service;

import app.application.order.port.in.query.FindAllOrdersQuery;
import app.domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class FindAllOrderService implements FindAllOrdersQuery {

    @Override
    public List<Order> execute() {
        return new ArrayList<>();
    }
}
