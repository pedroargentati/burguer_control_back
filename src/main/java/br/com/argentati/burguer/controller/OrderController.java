package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.enums.Status;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderDTO;
import br.com.argentati.burguer.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByEvent(@PageableDefault(size = 15) Pageable pageable, @PathVariable(name = "eventId") Long eventId) {
        return super.buildDefaultResponseForPage(orderService.getOrdersByEvent(pageable, eventId));
    }

    @GetMapping("/person")
    public ResponseEntity<Page<OrderDTO>> getOrdersByPerson(@PageableDefault(size = 15) Pageable pageable,  @RequestParam(name = "personId") Long personId) {
        Page<OrderDTO> page = orderService.getOrdersByPerson(pageable, personId);
        return super.buildDefaultResponseForPage(page);
    }

    @GetMapping("/person/period")
    public ResponseEntity<Page<OrderDTO>> getOrdersByPersonAndDateRange(
            @PageableDefault(size = 15) Pageable pageable,
            @RequestParam(name = "personId") Long personId,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Page<OrderDTO> page = orderService.getOrdersByPersonAndDateRange(pageable, personId, startDate, endDate);
        return super.buildDefaultResponseForPage(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) throws RecordNotFoundException {
        return super.buildResponseForEntity(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        OrderDTO orderCreated = orderService.createOrder(orderDTO);

        return super.buildResponseForPost(orderCreated, orderCreated.id());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        OrderDTO orderUpdated = orderService.updateOrder(id, orderDTO);

        return super.buildResponseForEntity(orderUpdated);
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<OrderDTO> patchOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) throws RecordNotFoundException {
        OrderDTO orderUpdated = orderService.updateOrderStatus(id, Status.fromString(orderDTO.status()));

        return super.buildResponseForEntity(orderUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Long id) throws RecordNotFoundException {
        var order = orderService.deleteOrder(id);

        return super.buildResponseForDelete(order);
    }

}
