package app.application.order.service;

import app.application.order.port.in.usecase.CreateOrderCommand;
import app.application.order.port.out.PersistOrderPort;
import app.domain.order.Order;
import app.domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateOrderServiceTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;

    @Mock
    private PersistOrderPort persistOrderPort;

    @InjectMocks
    private CreateOrderService createOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateOrder() {
        // given
        when(persistOrderPort.persist(any())).thenReturn(createOrder());

        // when
        Order order = createOrderService.apply(new CreateOrderCommand());

        // then
        verify(persistOrderPort, times(1)).persist(any());
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(OrderStatus.NEW));
    }

    private Order createOrder() {
        return new Order(
                ID,
                VERSION,
                ORDER_STATUS);
    }
}