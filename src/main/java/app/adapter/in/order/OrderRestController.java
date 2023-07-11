package app.adapter.in.order;

import app.application.order.port.in.facade.OrderFacade;
import app.application.order.port.in.usecase.CreateOrderCommand;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import app.domain.order.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@RestController
@RequestMapping({"/api/order"})
@RequiredArgsConstructor
public class OrderRestController {

    private static final String ORDER_NOT_FOUND = "The order cannot be found by given id: ";

    private final OrderFacade orderFacade;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderFacade.findAllOrders();
    }

    @GetMapping({"/{id}"})
    public Order getOrderById(@PathVariable Long id) {
        return orderFacade.findOrderById(id).orElseThrow(() -> new DomainEntityNotFoundException(ORDER_NOT_FOUND + id));
    }

    @PostMapping
    public Order createOrder(@Validated @RequestBody OrderWebTO orderWebTO) {
        return orderFacade.createOrder(createCreateOrderCommand(orderWebTO));
    }

    @PutMapping({"/confirm/{id}"})
    public Order confirmOrder(@PathVariable Long id) {
        return orderFacade.confirmOrder(id);
    }

    @PutMapping({"/cancel/{id}"})
    public Order cancelOrder(@PathVariable Long id) {
        return orderFacade.cancelOrder(id);
    }

    @PutMapping({"/complete/{id}"})
    public Order completeOrder(@PathVariable Long id) {
        return orderFacade.completeOrder(id);
    }

    @PostMapping("/dummy")
    public List<Order> createDummyOrders() {
        Random random = new Random();
        PaymentMethod[] paymentMethods = PaymentMethod.values();

        IntStream.range(0, 10).forEach(i -> {
            int randomPrice = random.nextInt(91) + 10;
            BigDecimal price = BigDecimal.valueOf(randomPrice);
            PaymentMethod paymentMethod = paymentMethods[random.nextInt(paymentMethods.length)];
            String name = "Order " + (i + 1);
            String description = "Dummy order " + (i + 1);

            OrderWebTO orderWebTO = new OrderWebTO(price, paymentMethod, name, description);
            orderFacade.createOrder(createCreateOrderCommand(orderWebTO));
        });

        return orderFacade.findAllOrders();
    }

    private CreateOrderCommand createCreateOrderCommand(OrderWebTO orderWebTO) {
        return new CreateOrderCommand(orderWebTO.getPrice(), orderWebTO.getPaymentMethod(), orderWebTO.getName(), orderWebTO.getDescription());
    }
}
