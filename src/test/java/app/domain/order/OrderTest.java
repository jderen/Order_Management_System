package app.domain.order;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    @Test
    void shouldCreateNewOrder() {
        // when
        Order order = Order.createNewOrder(PRICE, PAYMENT_METHOD, NAME, DESCRIPTION);

        // then
        assertOrderAttributes(order);
    }

    @Test
    void shouldConfirmOrder() {
        // given
        Order order = createOrder();

        // when
        order.confirm();

        // then
        assertThat(order.getOrderStatus(), is(OrderStatus.CONFIRMED));
    }

    @Test
    void shouldCancelOrder() {
        // given
        Order order = createOrder();

        // when
        order.cancel();

        // then
        assertThat(order.getOrderStatus(), is(OrderStatus.CANCELLED));
    }

    @Test
    void shouldCompleteOrder() {
        // given
        Order order = createOrder();

        // when
        order.complete();

        // then
        assertThat(order.getOrderStatus(), is(OrderStatus.COMPLETED));
    }

    private void assertOrderAttributes(Order order) {
        assertNotNull(order);
        assertThat(order.getOrderStatus(), is(ORDER_STATUS));
        assertThat(order.getPrice(), is(PRICE));
        assertThat(order.getPaymentMethod(), is(PAYMENT_METHOD));
        assertThat(order.getName(), is(NAME));
        assertThat(order.getDescription(), is(DESCRIPTION));
    }

    private Order createOrder() {
        return new Order(
                ID,
                VERSION,
                ORDER_STATUS,
                ORDER_DATE,
                PRICE,
                PAYMENT_METHOD,
                NAME,
                DESCRIPTION);
    }
}