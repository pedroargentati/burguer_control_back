package br.com.argentati.burguer.model.dto.response;

import br.com.argentati.burguer.model.entity.Ingredient;

import java.util.Set;

public record OrderIngredientResponseDTO(Long orderId, Set<Ingredient> ingredients) {
}
