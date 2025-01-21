package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.OrderIngredient;

public record OrderIngredientDTO(
        Long orderId,
        Long ingredientId,
        String ingredientName,
        Integer quantity
) {
    public OrderIngredientDTO(OrderIngredient orderIngredient) {
        this(orderIngredient.getId().getOrder().getId(), orderIngredient.getId().getIngredient().getId(),
                orderIngredient.getId().getIngredient().getName(), orderIngredient.getQuantity());
    }
}
