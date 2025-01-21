package br.com.argentati.burguer.model.dto;

public record OrderIngredientDTO(
        Long orderId,
        Long ingredientId,
        String ingredientName,
        Integer quantity
) {
}
