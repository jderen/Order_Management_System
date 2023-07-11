package app.application.order.port.in.facade.impl;

import app.application.order.port.in.query.FindAllOrdersQuery;
import app.application.order.port.in.query.FindOrderByIdQuery;
import app.application.order.port.in.usecase.CancelOrderUseCase;
import app.application.order.port.in.usecase.CompleteOrderUseCase;
import app.application.order.port.in.usecase.ConfirmOrderUseCase;
import app.application.order.port.in.usecase.CreateOrderCommand;
import app.application.order.port.in.usecase.CreateOrderUseCase;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderFacadeImplTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    @Mock
    private FindAllOrdersQuery findAllOrdersQuery;

    @Mock
    private FindOrderByIdQuery findOrderByIdQuery;

    @Mock
    private CancelOrderUseCase cancelOrderUseCase;

    @Mock
    private CompleteOrderUseCase completeOrderUseCase;

    @Mock
    private ConfirmOrderUseCase confirmOrderUseCase;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @InjectMocks
    private OrderFacadeImpl orderFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindAllOrders() {
        // given
        when(findAllOrdersQuery.execute()).thenReturn(singletonList(createOrder(OrderStatus.CONFIRMED)));

        // when
        List<Order> orders = orderFacade.findAllOrders();

        // then
        verify(findAllOrdersQuery, times(1)).execute();
        assertTrue(!orders.isEmpty());
        assertOrderAttributes(orders.get(0), OrderStatus.CONFIRMED);
    }

    @Test
    void shouldFindOrderById() {
        // given
        when(findOrderByIdQuery.execute(ID)).thenReturn(Optional.of(createOrder(OrderStatus.CONFIRMED)));

        // when
        Optional<Order> order = orderFacade.findOrderById(ID);

        // then
        verify(findOrderByIdQuery, times(1)).execute(ID);
        assertTrue(order.isPresent());
        assertOrderAttributes(order.get(), OrderStatus.CONFIRMED);
    }

    @Test
    void shouldCreateOrder() {
        // given
        CreateOrderCommand command = new CreateOrderCommand(PRICE, PAYMENT_METHOD, NAME, DESCRIPTION);
        when(createOrderUseCase.apply(command)).thenReturn(createOrder(OrderStatus.NEW));

        // when
        Order order = orderFacade.createOrder(command);

        // then
        verify(createOrderUseCase, times(1)).apply(command);
        assertOrderAttributes(order, OrderStatus.NEW);
    }

    @Test
    void shouldConfirmOrder() {
        // given
        when(confirmOrderUseCase.apply(ID)).thenReturn(createOrder(OrderStatus.CONFIRMED));

        // when
        Order order = orderFacade.confirmOrder(ID);

        // then
        verify(confirmOrderUseCase, times(1)).apply(ID);
        assertOrderAttributes(order, OrderStatus.CONFIRMED);
    }

    @Test
    void shouldCancelOrder() {
        // given
        when(cancelOrderUseCase.apply(ID)).thenReturn(createOrder(OrderStatus.CANCELLED));

        // when
        Order order = orderFacade.cancelOrder(ID);

        // then
        verify(cancelOrderUseCase, times(1)).apply(ID);
        assertOrderAttributes(order, OrderStatus.CANCELLED);
    }

    @Test
    void shouldCompleteOrder() {
        // given
        when(completeOrderUseCase.apply(ID)).thenReturn(createOrder(OrderStatus.COMPLETED));

        // when
        Order order = orderFacade.completeOrder(ID);

        // then
        verify(completeOrderUseCase, times(1)).apply(ID);
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