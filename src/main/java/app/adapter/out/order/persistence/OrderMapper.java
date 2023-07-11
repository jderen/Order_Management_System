package app.adapter.out.order.persistence;

import app.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderMapper {

    Order mapToDomain(OrderEntity entity);

    OrderEntity mapToPersistenceEntity(Order order);

    @Mapping(target = "id", ignore = true)
    void mapExistingEntity(Order order, @MappingTarget OrderEntity orderEntity);
}
