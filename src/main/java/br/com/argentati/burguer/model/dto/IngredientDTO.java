package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Ingredient;

public record IngredientDTO(
        Long id,
        String name,
        String category,
        Boolean byPass
) {
    public IngredientDTO(Ingredient ingredient) {
        this(ingredient.getId(), ingredient.getName(), ingredient.getCategory(), Boolean.FALSE);
    }
}
