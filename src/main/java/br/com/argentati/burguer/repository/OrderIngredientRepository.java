package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.OrderIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderIngredientRepository extends JpaRepository<OrderIngredient, OrderIngredient.OrderIngredientId> {
    List<OrderIngredient> findByIdOrderId(Long orderId);

    @Query("SELECT COUNT(oi) > 0 FROM OrderIngredient oi WHERE oi.id.order.id = :orderId AND oi.id.ingredient.id = :ingredientId")
    boolean existsByOrderIdAndIngredientId(@Param("orderId") Long orderId, @Param("ingredientId") Long ingredientId);
}
