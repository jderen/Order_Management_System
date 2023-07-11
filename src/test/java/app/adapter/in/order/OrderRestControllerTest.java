package app.adapter.in.order;

import app.application.order.port.in.facade.OrderFacade;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import app.domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        assertThat(orders.get(0).getId(), is(ID));
        assertThat(orders.get(0).getVersion(), is(VERSION));
        assertThat(orders.get(0).getOrderStatus(), is(OrderStatus.NEW));
    }

    @Test
    void shouldGetOrderById() {
        // given
        when(orderFacade.findOrderById(ID)).thenReturn(Optional.of(createOrder(OrderStatus.NEW)));

        // when
        Order order = orderRestController.getOrderById(ID);

        // then
        verify(orderFacade, times(1)).findOrderById(ID);
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.NEW));
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
        Order order = orderRestController.createOrder(new OrderWebTO());

        // then
        verify(orderFacade, times(1)).createOrder(any());
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.NEW));
    }

    @Test
    void shouldConfirmOrder() {
        // given
        when(orderFacade.confirmOrder(ID)).thenReturn(createOrder(OrderStatus.CONFIRMED));

        // when
        Order order = orderRestController.confirmOrder(ID);

        // then
        verify(orderFacade, times(1)).confirmOrder(ID);
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.CONFIRMED));
    }

    @Test
    void shouldCancelOrder() {
        // given
        when(orderFacade.cancelOrder(ID)).thenReturn(createOrder(OrderStatus.CANCELLED));

        // when
        Order order = orderRestController.cancelOrder(ID);

        // then
        verify(orderFacade, times(1)).cancelOrder(ID);
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.CANCELLED));
    }

    @Test
    void shouldCompleteOrder() {
        // given
        when(orderFacade.completeOrder(ID)).thenReturn(createOrder(OrderStatus.COMPLETED));

        // when
        Order order = orderRestController.completeOrder(ID);

        // then
        verify(orderFacade, times(1)).completeOrder(ID);
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.COMPLETED));
    }

    private Order createOrder(OrderStatus orderStatus) {
        return new Order(
                ID,
                VERSION,
                orderStatus);
    }

}