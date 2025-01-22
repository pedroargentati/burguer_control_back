package br.com.argentati.burguer.service;

import br.com.argentati.burguer.enums.MeatDoneness;
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

    /**
     * Cria um pedido.
     * @param orderDTO Dados do pedido.
     * @return O pedido criado.
     * @throws RecordNotFoundException Se o evento ou a pessoa não forem encontrados.
     */
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws RecordNotFoundException {
        logger.info("Criando pedido: " + orderDTO);

        Event event = eventService.getEntityEvent(orderDTO.eventId());
        Person person = personService.getEntityPerson(orderDTO.personId());

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .notes(orderDTO.notes())
                .meatDoneness(MeatDoneness.fromString(orderDTO.meatDoneness()))
                .person(person)
                .event(event)
                .build();

        logger.info("Pedido à ser criado: " + order);

        return orderRepository.save(order);
    }

}
