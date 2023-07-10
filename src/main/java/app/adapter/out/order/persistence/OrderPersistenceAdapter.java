package app.adapter.out.order.persistence;

import app.application.order.port.out.FindAllOrdersPort;
import app.application.order.port.out.FindOrderPort;
import app.application.order.port.out.PersistOrderPort;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PersistOrderPort, FindOrderPort, FindAllOrdersPort {

    private final OrderEntityRepository repository;
    private final OrderMapper mapper;

    @Override
    public List<Order> find() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToDomain)
                .collect(toList());
    }

    @Override
    public Optional<Order> find(Long id) {
        return repository.findById(id).map(mapper::mapToDomain);
    }

    @Override
    public Order persist(Order order) {
        return mapper.mapToDomain(repository.save(mapToEntity(order)));
    }

    private OrderEntity mapToEntity(Order order) {
        OrderEntity orderEntity;
        if (order.getId() != null) {
            orderEntity = repository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
            mapper.mapExistingEntity(order, orderEntity);
        } else {
            orderEntity = mapper.mapToPersistenceEntity(order);
        }
        return orderEntity;
    }
}
