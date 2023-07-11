package app.adapter.out.order.persistence;

import app.domain.order.Order;
import app.domain.order.OrderStatus;
import app.domain.order.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderPersistenceAdapterTest {

    private static final Long ID = 1L;
    private static final Long VERSION = 2L;
    private static final OrderStatus ORDER_STATUS = OrderStatus.CONFIRMED;
    private static final BigDecimal PRICE = new BigDecimal(1.50);
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final String NAME = "Order name";
    private static final String DESCRIPTION = "Test description";
    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();

    @Mock
    private OrderEntityRepository repository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderPersistenceAdapter orderPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindAllOrders() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        when(repository.findAll()).thenReturn(singletonList(orderEntity));
        when(mapper.mapToDomain(orderEntity)).thenReturn(createOrder());

        // when
        List<Order> orders = orderPersistenceAdapter.find();

        // then
        verify(repository, times(1)).findAll();
        assertTrue(!orders.isEmpty());
        assertOrderAttributes(orders.get(0));
    }

    @Test
    void shouldFindOrderById() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        when(repository.findById(ID)).thenReturn(Optional.of(orderEntity));
        when(mapper.mapToDomain(orderEntity)).thenReturn(createOrder());

        // when
        Optional<Order> order = orderPersistenceAdapter.find(ID);

        // then
        verify(repository, times(1)).findById(ID);
        verify(mapper, times(1)).mapToDomain(orderEntity);
        assertTrue(order.isPresent());
        assertOrderAttributes(order.get());
    }

    @Test
    void shouldPersistOrderWhenIDExists() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        Order order = createOrder();
        when(repository.findById(ID)).thenReturn(Optional.of(orderEntity));
        when(mapper.mapToDomain(orderEntity)).thenReturn(order);
        when(repository.save(orderEntity)).thenReturn(orderEntity);

        // when
        Order savedOrder = orderPersistenceAdapter.persist(order);

        // then
        verify(repository, times(1)).findById(ID);
        verify(mapper, times(1)).mapToDomain(orderEntity);
        verify(mapper, times(1)).mapExistingEntity(order, orderEntity);
        verify(repository, times(1)).save(orderEntity);
        verify(mapper, never()).mapToPersistenceEntity(order);
        assertOrderAttributes(savedOrder);
    }

    @Test
    void shouldPersistOrderWhenIDDoesNotExist() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        Order order = new Order(null, null, ORDER_STATUS, ORDER_DATE, PRICE, PAYMENT_METHOD, NAME, DESCRIPTION);
        when(mapper.mapToDomain(orderEntity)).thenReturn(createOrder());
        when(repository.save(orderEntity)).thenReturn(orderEntity);
        when(mapper.mapToPersistenceEntity(order)).thenReturn(orderEntity);

        // when
        Order savedOrder = orderPersistenceAdapter.persist(order);

        // then
        verify(mapper, times(1)).mapToDomain(orderEntity);
        verify(repository, times(1)).save(orderEntity);
        verify(mapper, times(1)).mapToPersistenceEntity(order);
        verify(mapper, never()).mapExistingEntity(order, orderEntity);
        verify(repository, never()).findById(ID);
        assertOrderAttributes(savedOrder);
    }

    @Test
    void shouldThrowDomainEntityNotFoundExceptionWhenThereIsNoOrderWithGivenId() {
        // given
        OrderEntity orderEntity = createOrderEntity();
        Order order = createOrder();
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // when + then
        assertThrows(EntityNotFoundException.class, () ->
                orderPersistenceAdapter.persist(order));

        verify(repository, times(1)).findById(ID);
        verify(mapper, never()).mapToDomain(orderEntity);
        verify(repository, never()).save(orderEntity);
        verify(mapper, never()).mapToPersistenceEntity(order);
        verify(mapper, never()).mapExistingEntity(order, orderEntity);
    }

    private void assertOrderAttributes(Order order) {
        assertNotNull(order);
        assertThat(order.getId(), is(ID));
        assertThat(order.getVersion(), is(VERSION));
        assertThat(order.getOrderStatus(), is(ORDER_STATUS));
        assertThat(order.getOrderDate(), is(ORDER_DATE));
        assertThat(order.getPrice(), is(PRICE));
        assertThat(order.getPaymentMethod(), is(PAYMENT_METHOD));
        assertThat(order.getName(), is(NAME));
        assertThat(order.getDescription(), is(DESCRIPTION));
    }

    private OrderEntity createOrderEntity() {
        return new OrderEntity(ID, VERSION, ORDER_STATUS, ORDER_DATE, PRICE, PAYMENT_METHOD, NAME, DESCRIPTION);
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