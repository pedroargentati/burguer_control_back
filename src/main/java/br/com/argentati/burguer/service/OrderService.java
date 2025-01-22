package br.com.argentati.burguer.service;

import br.com.argentati.burguer.enums.MeatDoneness;
import br.com.argentati.burguer.exception.OrderAlreadyExistsException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderDTO;
import br.com.argentati.burguer.model.entity.Event;
import br.com.argentati.burguer.model.entity.Order;
import br.com.argentati.burguer.model.entity.Person;
import br.com.argentati.burguer.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private final OrderRepository orderRepository;

    private final EventService eventService;

    private final PersonService personService;

    @Autowired
    public OrderService(OrderRepository orderRepository, EventService eventService, PersonService personService) {
        this.orderRepository = orderRepository;
        this.eventService = eventService;
        this.personService = personService;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws RecordNotFoundException, OrderAlreadyExistsException {
        logger.info("Iniciando criação do pedido: " + orderDTO);

        Event event = eventService.getEntityEvent(orderDTO.eventId());
        Person person = personService.getEntityPerson(orderDTO.personId());

        // Verifica se já existe um pedido para a pessoa
        orderRepository.findOrderByPersonId(person.getId()).ifPresent(existingOrder -> {
            throw new OrderAlreadyExistsException(String.format("Já existe um pedido no evento %s para o (a) %s.", event.getName(), person.getName()));
        });

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .notes(orderDTO.notes())
                .meatDoneness(MeatDoneness.fromString(orderDTO.meatDoneness()))
                .person(person)
                .event(event)
                .build();

        logger.info("Pedido sendo salvo: " + order);

        return orderRepository.save(order);
    }

}
