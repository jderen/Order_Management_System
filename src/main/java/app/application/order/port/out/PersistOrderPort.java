package app.application.order.port.out;

import app.domain.order.Order;

public interface PersistOrderPort {

    Order persist(Order order);
}
