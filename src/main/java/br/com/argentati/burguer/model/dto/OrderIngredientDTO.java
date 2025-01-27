package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.OrderIngredient;

import java.util.Collections;
import java.util.Set;

public record OrderIngredientDTO(
        Long orderId,
        Set<Long> ingredientList,
        String ingredientName,
        Integer quantity
) {

    public OrderIngredientDTO(OrderIngredient orderIngredient) {
        this(orderIngredient.getId().getOrder().getId(), Collections.emptySet(),
                orderIngredient.getId().getIngredient().getName(), orderIngredient.getQuantity());
    }
}
