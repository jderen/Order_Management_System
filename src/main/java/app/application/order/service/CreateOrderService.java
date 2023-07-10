package app.application.order.service;

import app.application.order.port.in.usecase.CreateOrderCommand;
import app.application.order.port.in.usecase.CreateOrderUseCase;
import app.application.order.port.out.PersistOrderPort;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static app.domain.order.Order.createNewOrder;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final PersistOrderPort persistOrderPort;

    @Override
    public Order apply(CreateOrderCommand command) {
        return persistOrderPort.persist(createNewOrder());
    }
}
