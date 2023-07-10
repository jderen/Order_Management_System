package app.adapter.in.order;

import app.application.order.port.in.facade.OrderFacade;
import app.application.order.port.in.usecase.CreateOrderCommand;
import app.domain.foundation.DomainEntityNotFoundException;
import app.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Order createOrder(@RequestBody Order order) {
        return orderFacade.createOrder(new CreateOrderCommand());
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
}
