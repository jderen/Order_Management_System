package app.domain.order;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OrderTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;

    @Test
    void shouldCreateNewOrder() {
        // when
        Order order = Order.createNewOrder();

        // then
        assertThat(order.getOrderStatus(), is(OrderStatus.NEW));
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

    private Order createOrder() {
        return new Order(
                ID,
                VERSION,
                OrderStatus.NEW);
    }
}