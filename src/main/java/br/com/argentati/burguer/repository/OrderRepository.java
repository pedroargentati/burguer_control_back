package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByPersonIdAndEventId(Long personId, Long eventId);

    Page<Order> findOrdersByEventId(Long eventId, Pageable pageable);

}
