package app.adapter.in.order;

import app.application.order.port.in.facade.OrderFacade;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import app.domain.order.OrderStatus;
import app.domain.order.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderRestControllerTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    @Mock
    private OrderFacade orderFacade;

    @InjectMocks
    private OrderRestController orderRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetAllOrders() {
        // given
        when(orderFacade.findAllOrders()).thenReturn(singletonList(createOrder(OrderStatus.NEW)));

        // when
        List<Order> orders = orderRestController.getAllOrders();

        // then
        verify(orderFacade, times(1)).findAllOrders();
        assertTrue(!orders.isEmpty());
        assertOrderAttributes(orders.get(0), OrderStatus.NEW);
    }

    @Test
    void shouldGetOrderById() {
        // given
        when(orderFacade.findOrderById(ID)).thenReturn(Optional.of(createOrder(OrderStatus.NEW)));

        // when
        Order order = orderRestController.getOrderById(ID);

        // then
        verify(orderFacade, times(1)).findOrderById(ID);
        assertOrderAttributes(order, OrderStatus.NEW);
    }

    @Test
    void shouldThrowDomainEntityNotFoundExceptionWhenThereIsNoOrderWithGivenId() {
        // given
        when(orderFacade.findOrderById(ID)).thenReturn(Optional.empty());

        // when + then
        assertThrows(DomainEntityNotFoundException.class, () ->
                orderRestController.getOrderById(ID));

        verify(orderFacade, times(1)).findOrderById(ID);
    }

    @Test
    void shouldCreateOrder() {
        // given
        when(orderFacade.createOrder(any())).thenReturn(createOrder(OrderStatus.NEW));

        // when
        Order order = orderRestController.createOrder(new OrderWebTO(PRICE, PAYMENT_METHOD, NAME, DESCRIPTION));

        // then
        verify(orderFacade, times(1)).createOrder(any());
        assertOrderAttributes(order, OrderStatus.NEW);
    }

    @Test
    void shouldConfirmOrder() {
        // given
        when(orderFacade.confirmOrder(ID)).thenReturn(createOrder(OrderStatus.CONFIRMED));

        // when
        Order order = orderRestController.confirmOrder(ID);

        // then
        verify(orderFacade, times(1)).confirmOrder(ID);
        assertOrderAttributes(order, OrderStatus.CONFIRMED);
    }

    @Test
    void shouldCancelOrder() {
        // given
        when(orderFacade.cancelOrder(ID)).thenReturn(createOrder(OrderStatus.CANCELLED));

        // when
        Order order = orderRestController.cancelOrder(ID);

        // then
        verify(orderFacade, times(1)).cancelOrder(ID);
        assertOrderAttributes(order, OrderStatus.CANCELLED);
    }

    @Test
    void shouldCompleteOrder() {
        // given
        when(orderFacade.completeOrder(ID)).thenReturn(createOrder(OrderStatus.COMPLETED));

        // when
        Order order = orderRestController.completeOrder(ID);

        // then
        verify(orderFacade, times(1)).completeOrder(ID);
        assertOrderAttributes(order, OrderStatus.COMPLETED);
    }

    private void assertOrderAttributes(Order order, OrderStatus orderStatus) {
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(orderStatus));
        assertThat(order.getOrderDate(), is(ORDER_DATE));
        assertThat(order.getPrice(), is(PRICE));
        assertThat(order.getPaymentMethod(), is(PAYMENT_METHOD));
        assertThat(order.getName(), is(NAME));
        assertThat(order.getDescription(), is(DESCRIPTION));
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