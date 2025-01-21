package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
