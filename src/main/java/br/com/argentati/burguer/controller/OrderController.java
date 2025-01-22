package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderDTO;
import br.com.argentati.burguer.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        orderService.createOrder(orderDTO);
    }

}
