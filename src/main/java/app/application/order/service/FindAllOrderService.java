package app.application.order.service;

import app.application.order.port.in.query.FindAllOrdersQuery;
import app.application.order.port.out.FindAllOrdersPort;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllOrderService implements FindAllOrdersQuery {

    private final FindAllOrdersPort findAllOrdersPort;

    @Override
    @Transactional(readOnly = true)
    public List<Order> execute() {
        return findAllOrdersPort.find();
    }
}
