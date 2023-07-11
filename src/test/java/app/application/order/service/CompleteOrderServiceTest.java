package app.application.order.service;

import app.application.order.port.out.FindOrderPort;
import app.application.order.port.out.PersistOrderPort;
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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompleteOrderServiceTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    @Mock
    private PersistOrderPort persistOrderPort;

    @Mock
    private FindOrderPort findOrderPort;

    @InjectMocks
    private CompleteOrderService completeOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCompleteOrder() {
        // given
        Order order = createOrder(OrderStatus.NEW);
        when(findOrderPort.find(ID)).thenReturn(Optional.of(order));
        Order completedOrder = createOrder(OrderStatus.COMPLETED);
        when(persistOrderPort.persist(any())).thenReturn(completedOrder);

        // when
        Order result = completeOrderService.apply(ID);

        // then
        verify(findOrderPort, times(1)).find(ID);
        verify(persistOrderPort, times(1)).persist(any());
        assertOrderAttributes(result);
    }

    @Test
    void shouldThrowDomainEntityNotFoundExceptionWhenThereIsNoOrderWithGivenId() {
        // given
        when(findOrderPort.find(ID)).thenReturn(Optional.empty());

        // when + then
        assertThrows(DomainEntityNotFoundException.class, () ->
                completeOrderService.apply(ID));

        verify(findOrderPort, times(1)).find(ID);
        verify(persistOrderPort, never()).persist(any());
    }

    private void assertOrderAttributes(Order order) {
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.COMPLETED));
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