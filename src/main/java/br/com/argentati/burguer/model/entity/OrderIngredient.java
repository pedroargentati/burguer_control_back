package br.com.argentati.burguer.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_ingredients")
@Data
@NoArgsConstructor
public class OrderIngredient {

    @EmbeddedId
    private OrderIngredientId id;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class OrderIngredientId {

        @ManyToOne
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;

        @ManyToOne
        @JoinColumn(name = "ingredient_id", nullable = false)
        private Ingredient ingredient;
    }
}
