package app.application.order.service;

import app.application.order.port.out.FindAllOrdersPort;
import app.domain.order.Order;
import app.domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAllOrderServiceTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;

    @Mock
    private FindAllOrdersPort findAllOrdersPort;

    @InjectMocks
    private FindAllOrderService findAllOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindOrderById() {
        // given
        when(findAllOrdersPort.find()).thenReturn(singletonList(createOrder()));

        // when
        List<Order> orders = findAllOrderService.execute();

        // then
        verify(findAllOrdersPort, times(1)).find();
        assertTrue(!orders.isEmpty());
        assertThat(orders.size(), is(1));
        assertThat(orders.get(0).getId(), is(ID));
        assertThat(orders.get(0).getVersion(), is(VERSION));
        assertThat(orders.get(0).getOrderStatus(), is(ORDER_STATUS));
    }

    private Order createOrder() {
        return new Order(
                ID,
                VERSION,
                ORDER_STATUS);
    }
}