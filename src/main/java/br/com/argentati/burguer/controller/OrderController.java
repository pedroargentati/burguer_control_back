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
    public Page<OrderDTO> getOrders(@PageableDefault(size = 15) Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @GetMapping("/event")
    public Page<OrderDTO> getOrdersByEvent(@PageableDefault(size = 15) Pageable pageable, @RequestParam(name = "eventId") Long eventId) {
        return orderService.getOrdersByEvent(pageable, eventId);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        Order orderCreated = orderService.createOrder(orderDTO);

        return super.buildResponseForPost(orderCreated, orderCreated.getId());
    }

}
