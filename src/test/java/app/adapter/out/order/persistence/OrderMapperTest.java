package app.adapter.out.order.persistence;

import app.domain.order.Order;
import app.domain.order.OrderStatus;
import app.domain.order.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderMapperTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.CONFIRMED;
    private static final OrderStatus CHANGED_ORDER_STATUS = OrderStatus.NEW;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    private final OrderMapper mapper = new OrderMapperImpl();

    @Test
    void shouldMapEntityToDomain() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        // when
        Order order = mapper.mapToDomain(orderEntity);
        // then
        assertOrderAttributes(order);
    }

    @Test
    void shouldMapToPersistenceEntity() {
        // given
        Order order = createOrder(ORDER_STATUS);

        // when
        OrderEntity orderEntity = mapper.mapToPersistenceEntity(order);

        // then
        assertOrderEntityAttributes(orderEntity, ORDER_STATUS);
    }

    @Test
    void shouldMapExistingEntity() {
        // given
        Order changedOrder = createOrder(CHANGED_ORDER_STATUS);
        OrderEntity orderEntity = createOrderEntity();

        // when
        mapper.mapExistingEntity(changedOrder, orderEntity);

        // then
        assertOrderEntityAttributes(orderEntity, CHANGED_ORDER_STATUS);
    }

    private void assertOrderAttributes(Order order) {
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(ORDER_STATUS));
        assertThat(order.getOrderDate(), is(ORDER_DATE));
        assertThat(order.getPrice(), is(PRICE));
        assertThat(order.getPaymentMethod(), is(PAYMENT_METHOD));
        assertThat(order.getName(), is(NAME));
        assertThat(order.getDescription(), is(DESCRIPTION));
    }

    private void assertOrderEntityAttributes(OrderEntity orderEntity, OrderStatus orderStatus) {
        assertNotNull(orderEntity);
        assertThat(orderEntity.getId(), is(ID));
        assertThat(orderEntity.getVersion(), is(VERSION));
        assertThat(orderEntity.getOrderStatus(), is(orderStatus));
        assertThat(orderEntity.getOrderDate(), is(ORDER_DATE));
        assertThat(orderEntity.getPrice(), is(PRICE));
        assertThat(orderEntity.getPaymentMethod(), is(PAYMENT_METHOD));
        assertThat(orderEntity.getName(), is(NAME));
        assertThat(orderEntity.getDescription(), is(DESCRIPTION));
    }

    private OrderEntity createOrderEntity() {
        return new OrderEntity(ID, VERSION, ORDER_STATUS, ORDER_DATE, PRICE, PAYMENT_METHOD, NAME, DESCRIPTION);
    }

    private Order createOrder(OrderStatus orderStatus) {
        return new Order(
                ID,
                VERSION,
                orderStatus,
                ORDER_DATE,
                PRICE,
                PAYMENT_METHOD,
                NAME,
                DESCRIPTION);
    }
}