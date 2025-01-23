package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByPersonIdAndEventId(Long personId, Long eventId);

    Page<Order> findOrdersByEventId(Long eventId, Pageable pageable);

    Page<Order> findOrdersByPersonId(Long personId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.person.id = :personId AND o.createdAt BETWEEN :startDate AND :endDate")
    Page<Order> findOrdersByPersonIdAndDateRange(Pageable pageable,
                                                 @Param("personId") Long personId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
