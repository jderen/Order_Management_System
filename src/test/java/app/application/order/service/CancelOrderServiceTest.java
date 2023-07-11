package app.application.order.service;

import app.application.order.port.out.FindOrderPort;
import app.application.order.port.out.PersistOrderPort;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import app.domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

class CancelOrderServiceTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;

    @Mock
    private PersistOrderPort persistOrderPort;

    @Mock
    private FindOrderPort findOrderPort;

    @InjectMocks
    private CancelOrderService cancelOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCancelOrder() {
        // given
        Order order = createOrder(OrderStatus.NEW);
        when(findOrderPort.find(ID)).thenReturn(Optional.of(order));
        Order cancelledOrder = createOrder(OrderStatus.CANCELLED);
        when(persistOrderPort.persist(any())).thenReturn(cancelledOrder);

        // when
        Order result = cancelOrderService.apply(ID);

        // then
        verify(findOrderPort, times(1)).find(ID);
        verify(persistOrderPort, times(1)).persist(any());
        assertNotNull(result);
        assertThat(result.getId(), is(ID));
        assertThat(result.getVersion(), is(VERSION));
        assertThat(result.getOrderStatus(), is(OrderStatus.CANCELLED));
    }

    @Test
    void shouldThrowDomainEntityNotFoundExceptionWhenThereIsNoOrderWithGivenId() {
        // given
        when(findOrderPort.find(ID)).thenReturn(Optional.empty());

        // when + then
        assertThrows(DomainEntityNotFoundException.class, () ->
                cancelOrderService.apply(ID));

        verify(findOrderPort, times(1)).find(ID);
        verify(persistOrderPort, never()).persist(any());
    }

    private Order createOrder(OrderStatus orderStatus) {
        return new Order(
                ID,
                VERSION,
                orderStatus);
    }
}