package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.OrderIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderIngredientRepository extends JpaRepository<OrderIngredient, OrderIngredient.OrderIngredientId> {
    List<OrderIngredient> findByIdOrderId(Long orderId);
}
