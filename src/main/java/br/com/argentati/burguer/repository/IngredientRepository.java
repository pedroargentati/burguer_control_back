package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE i.name = :name")
    Optional<Ingredient> findFirstByName(@Param("name") String name);


}
