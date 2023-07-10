package app.application.order.service;

import app.application.order.port.in.usecase.ConfirmOrderUseCase;
import app.application.order.port.out.FindOrderPort;
import app.application.order.port.out.PersistOrderPort;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmOrderService implements ConfirmOrderUseCase {

    private static final String ORDER_NOT_FOUND = "The order cannot be found by given id: ";

    private final PersistOrderPort persistOrderPort;
    private final FindOrderPort findOrderPort;

    @Override
    public Order apply(Long id) {
        Order order = findOrderPort.find(id).orElseThrow(() -> new DomainEntityNotFoundException(ORDER_NOT_FOUND + id));
        order.confirm();
        return persistOrderPort.persist(order);
    }
}
