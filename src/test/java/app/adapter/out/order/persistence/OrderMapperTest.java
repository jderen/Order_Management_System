package app.adapter.out.order.persistence;

import app.domain.order.Order;
import app.domain.order.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class OrderMapperTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.CONFIRMED;
    private static final OrderStatus CHANGED_ORDER_STATUS = OrderStatus.NEW;

    private final OrderMapper mapper = new OrderMapperImpl();

    @Test
    void shouldMapEntityToDomain() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        // when
        Order order = mapper.mapToDomain(orderEntity);
        // then
        assertThat(order.getId(), equalTo(orderEntity.getId()));
        assertThat(order.getVersion(), equalTo(orderEntity.getVersion()));
        assertThat(order.getOrderStatus(), equalTo(orderEntity.getOrderStatus()));
    }

    @Test
    void shouldMapToPersistenceEntity() {
        // given
        Order order = createOrder(ORDER_STATUS);

        // when
        OrderEntity orderEntity = mapper.mapToPersistenceEntity(order);

        // then
        assertThat(orderEntity.getId(), equalTo(order.getId()));
        assertThat(orderEntity.getVersion(), equalTo(order.getVersion()));
        assertThat(orderEntity.getOrderStatus(), equalTo(order.getOrderStatus()));
    }

    @Test
    void shouldMapExistingEntity() {
        // given
        Order changedOrder = createOrder(CHANGED_ORDER_STATUS);
        OrderEntity orderEntity = createOrderEntity();

        // when
        mapper.mapExistingEntity(changedOrder, orderEntity);

        // then
        assertThat(orderEntity.getId(), equalTo(changedOrder.getId()));
        assertThat(orderEntity.getVersion(), equalTo(changedOrder.getVersion()));
        assertThat(orderEntity.getOrderStatus(), equalTo(changedOrder.getOrderStatus()));
    }

    private OrderEntity createOrderEntity() {
        return new OrderEntity(ID, VERSION, ORDER_STATUS);
    }

    private Order createOrder(OrderStatus orderStatus) {
        return new Order(
                ID,
                VERSION,
                orderStatus);
    }
}