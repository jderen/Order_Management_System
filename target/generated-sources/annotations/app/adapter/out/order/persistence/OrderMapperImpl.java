package app.adapter.out.order.persistence;

import app.domain.order.Order;
import app.domain.order.OrderStatus;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-10T14:44:09+0200",
    comments = "version: 1.5.0.Beta2, compiler: javac, environment: Java 1.8.0_192 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order mapToDomain(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        Long version = null;
        OrderStatus orderStatus = null;

        id = entity.getId();
        version = entity.getVersion();
        orderStatus = entity.getOrderStatus();

        Order order = new Order( id, version, orderStatus );

        return order;
    }

    @Override
    public OrderEntity mapToPersistenceEntity(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId( order.getId() );
        orderEntity.setVersion( order.getVersion() );
        orderEntity.setOrderStatus( order.getOrderStatus() );

        return orderEntity;
    }

    @Override
    public void mapExistingEntity(Order order, OrderEntity orderEntity) {
        if ( order == null ) {
            return;
        }

        orderEntity.setVersion( order.getVersion() );
        orderEntity.setOrderStatus( order.getOrderStatus() );
    }
}
