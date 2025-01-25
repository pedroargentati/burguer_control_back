package br.com.argentati.burguer.service;

import br.com.argentati.burguer.enums.MeatDoneness;
import br.com.argentati.burguer.enums.Status;
import br.com.argentati.burguer.exception.OrderAlreadyExistsException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderDTO;
import br.com.argentati.burguer.model.entity.Event;
import br.com.argentati.burguer.model.entity.Order;
import br.com.argentati.burguer.model.entity.Person;
import br.com.argentati.burguer.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Obtém os pedidos
     *
     * @param pageable Paginação
     * @return Pedidos
     */
    public Page<OrderDTO> getOrders(Pageable pageable) {
        logger.info("Obtendo pedidos...");

        return orderRepository.findAll(pageable)
                .map(OrderDTO::new);
    }

    /**
     * Obtém os pedidos de um evento
     *
     * @param pageable Paginação
     * @param eventId ID do evento
     * @return Pedidos do evento
     */
    public Page<OrderDTO> getOrdersByEvent(Pageable pageable, Long eventId) {
        logger.info(String.format("Obtendo pedidos do evento %s...", eventId));

        return orderRepository.findOrdersByEventId(eventId, pageable)
                .map(OrderDTO::new);
    }

    /**
     * Obtém os pedidos de uma pessoa
     *
     * @param pageable Paginação
     * @param personId ID da pessoa
     * @return Pedidos da pessoa
     */
    public Page<OrderDTO> getOrdersByPerson(Pageable pageable, Long personId) {
        logger.info(String.format("Obtendo pedidos do pessoa %s...", personId));

        return orderRepository.findOrdersByPersonId(personId, pageable)
                .map(OrderDTO::new);
    }

    /**
     * Obtém os pedidos de uma pessoa em um intervalo de datas
     *
     * @param pageable   Paginação
     * @param personId   ID da pessoa
     * @param startDate  Data inicial
     * @param endDate    Data final
     * @return Pedidos da pessoa no intervalo de datas
     */
    public Page<OrderDTO> getOrdersByPersonAndDateRange(Pageable pageable, Long personId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersByPersonIdAndDateRange(pageable, personId, startDate, endDate)
                .map(OrderDTO::new);
    }

    /**
     * Obtém um pedido por ID
     *
     * @param id ID do pedido
     * @return Pedido
     * @throws RecordNotFoundException
     */
    public OrderDTO getOrderById(Long id) throws RecordNotFoundException {
        logger.info("Obtendo pedido por ID: " + id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Pedido não encontrado."));

        return new OrderDTO(order);
    }

    /**
     * Obtém um pedido por ID
     *
     * @param id ID do pedido
     * @return Pedido
     * @throws RecordNotFoundException
     */
    public Order getOrderByIdEntity(Long id) throws RecordNotFoundException {
        logger.info("Obtendo pedido por ID: " + id);

        return orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Pedido não encontrado."));
    }

    /**
     * Cria um pedido
     *
     * @param orderDTO DTO com os dados do pedido
     * @return Pedido criado
     * @throws RecordNotFoundException
     * @throws OrderAlreadyExistsException
     */
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) throws RecordNotFoundException, OrderAlreadyExistsException {
        logger.info("Iniciando criação do pedido: " + orderDTO);

        Event event = eventService.getEntityEvent(orderDTO.eventId());
        Person person = personService.getEntityPerson(orderDTO.personId());

        // Verifica se já existe um pedido para a pessoa
        orderRepository.findOrderByPersonIdAndEventId(person.getId(), event.getId()).ifPresent(existingOrder -> {
            throw new OrderAlreadyExistsException(String.format("Já existe um pedido no evento %s para o (a) %s.", event.getName(), person.getName()));
        });

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .notes(orderDTO.notes())
                .meatDoneness(MeatDoneness.fromString(orderDTO.meatDoneness()))
                .person(person)
                .event(event)
                .status(Status.REALIZADO)
                .build();

        logger.info("Pedido sendo salvo: " + order);

        return new OrderDTO(orderRepository.save(order));
    }

    /**
     * Atualiza um pedido
     *
     * @param id       ID do pedido
     * @param orderDTO DTO com os dados do pedido
     * @return Pedido atualizado
     * @throws RecordNotFoundException
     */
    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) throws RecordNotFoundException {
        logger.info("Iniciando atualização do pedido: " + orderDTO);

        Order order = this.getOrderByIdEntity(id);

        order.update(orderDTO);

        logger.info("Pedido sendo atualizado: " + order);

        return new OrderDTO(orderRepository.save(order));
    }

    /**
     * Atualiza o status de um pedido
     *
     * @param id     ID do pedido
     * @param status Novo status
     * @return Pedido atualizado
     * @throws RecordNotFoundException
     */
    @Transactional
    public OrderDTO updateOrderStatus(Long id, Status status) throws RecordNotFoundException {
        logger.info("Iniciando atualização do status do pedido: " + id);

        Order order = this.getOrderByIdEntity(id);
        order.setStatus(status);

        logger.info("Pedido sendo atualizado: " + order);

        return new OrderDTO(orderRepository.save(order));
    }

    /**
     * Remove um pedido
     *
     * @param id ID do pedido
     * @throws RecordNotFoundException
     */
    @Transactional
    public OrderDTO deleteOrder(Long id) throws RecordNotFoundException {
        logger.info("Iniciando remoção do pedido: " + id);
        Order order = this.getOrderByIdEntity(id);

        orderRepository.delete(order);

        logger.info("Pedido removido: " + id);
        return new OrderDTO(order);
    }

}
