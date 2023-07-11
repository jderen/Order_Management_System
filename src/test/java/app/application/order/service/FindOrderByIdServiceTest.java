package app.application.order.service;

import app.application.order.port.out.FindOrderPort;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindOrderByIdServiceTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;

    @Mock
    private FindOrderPort findOrderPort;

    @InjectMocks
    private FindOrderByIdService findOrderByIdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindOrderById() {
        // given
        when(findOrderPort.find(ID)).thenReturn(Optional.of(createOrder()));

        // when
        Optional<Order> order = findOrderByIdService.execute(ID);

        // then
        verify(findOrderPort, times(1)).find(ID);
        assertTrue(order.isPresent());
        assertThat(order.get().getId(), is(ID));
        assertThat(order.get().getVersion(), is(VERSION));
        assertThat(order.get().getOrderStatus(), is(ORDER_STATUS));
    }

    private Order createOrder() {
        return new Order(
                ID,
                VERSION,
                ORDER_STATUS);
    }
}