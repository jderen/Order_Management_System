package app.application.order.service;

import app.application.order.port.in.query.FindOrderByIdQuery;
import app.application.order.port.out.FindOrderPort;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindOrderByIdService implements FindOrderByIdQuery {

    private final FindOrderPort findOrderPort;

    @Override
    public Optional<Order> execute(Long id) {
        return findOrderPort.find(id);
    }
}
