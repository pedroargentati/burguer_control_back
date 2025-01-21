package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
