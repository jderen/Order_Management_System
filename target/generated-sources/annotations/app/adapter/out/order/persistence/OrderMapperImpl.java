package app.adapter.out.order.persistence;

import app.domain.order.Order;
import app.domain.order.OrderStatus;
import app.domain.order.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-11T23:38:30+0200",
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
        LocalDateTime orderDate = null;
        BigDecimal price = null;
        PaymentMethod paymentMethod = null;
        String name = null;
        String description = null;

        id = entity.getId();
        version = entity.getVersion();
        orderStatus = entity.getOrderStatus();
        orderDate = entity.getOrderDate();
        price = entity.getPrice();
        paymentMethod = entity.getPaymentMethod();
        name = entity.getName();
        description = entity.getDescription();

        Order order = new Order( id, version, orderStatus, orderDate, price, paymentMethod, name, description );

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
        orderEntity.setOrderDate( order.getOrderDate() );
        orderEntity.setPrice( order.getPrice() );
        orderEntity.setPaymentMethod( order.getPaymentMethod() );
        orderEntity.setName( order.getName() );
        orderEntity.setDescription( order.getDescription() );

        return orderEntity;
    }

    @Override
    public void mapExistingEntity(Order order, OrderEntity orderEntity) {
        if ( order == null ) {
            return;
        }

        orderEntity.setVersion( order.getVersion() );
        orderEntity.setOrderStatus( order.getOrderStatus() );
        orderEntity.setOrderDate( order.getOrderDate() );
        orderEntity.setPrice( order.getPrice() );
        orderEntity.setPaymentMethod( order.getPaymentMethod() );
        orderEntity.setName( order.getName() );
        orderEntity.setDescription( order.getDescription() );
    }
}
