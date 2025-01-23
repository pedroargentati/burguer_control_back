package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderDTO;
import br.com.argentati.burguer.model.entity.Order;
import br.com.argentati.burguer.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController extends RestCommonService {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getOrders(@PageableDefault(size = 15) Pageable pageable) {
        return super.buildDefaultResponseForPage(orderService.getOrders(pageable));
    }

    @GetMapping("/event")
    public ResponseEntity<Page<OrderDTO>> getOrdersByEvent(@PageableDefault(size = 15) Pageable pageable, @RequestParam(name = "eventId") Long eventId) {
        return super.buildDefaultResponseForPage(orderService.getOrdersByEvent(pageable, eventId));
    }

    @GetMapping("/person")
    public ResponseEntity<Page<OrderDTO>> getOrdersByPerson(@PageableDefault(size = 15) Pageable pageable,  @RequestParam(name = "personId") Long personId) {
        Page<OrderDTO> page = orderService.getOrdersByPerson(pageable, personId);
        return super.buildDefaultResponseForPage(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) throws RecordNotFoundException {
        return super.buildResponseForEntity(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        Order orderCreated = orderService.createOrder(orderDTO);

        return super.buildResponseForPost(orderCreated, orderCreated.getId());
    }

}
