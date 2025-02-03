package br.com.argentati.burguer.service;

import br.com.argentati.burguer.enums.Status;
import br.com.argentati.burguer.exception.BusinessException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderIngredientDTO;
import br.com.argentati.burguer.model.dto.response.OrderIngredientResponseDTO;
import br.com.argentati.burguer.model.entity.Ingredient;
import br.com.argentati.burguer.model.entity.Order;
import br.com.argentati.burguer.model.entity.OrderIngredient;
import br.com.argentati.burguer.repository.OrderIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderIngredientService {

    private final OrderIngredientRepository orderIngredientRepository;

    private final OrderService orderService;

    private final IngredientService ingredientService;

    /**
     * Obtém um pedido de ingrediente
     *
     * @param orderId      ID do pedido
     * @param ingredientId ID do ingrediente
     * @return Pedido de ingrediente
     * @throws RecordNotFoundException Pedido não encontrado
     */
    public OrderIngredient getOrderIngredientByIdEntity(Long orderId, Long ingredientId) throws RecordNotFoundException {
        return orderIngredientRepository.findById(OrderIngredient.OrderIngredientId.builder()
                        .order(orderService.getOrderByIdEntity(orderId))
                        .ingredient(ingredientService.getIngredientEntity(ingredientId))
                        .build())
                .orElseThrow(() -> new RecordNotFoundException("Pedido não encontrado"));
    }

    public boolean existsByOrderIdAndIngredientId(Long orderId, Long ingredientId) {
        return orderIngredientRepository.existsByOrderIdAndIngredientId(orderId, ingredientId);
    }

    /**
     * Adiciona ingredientes a um pedido
     *
     * @param orderIngredientDTO Dados do pedido
     * @return Ingredientes
     * @throws RecordNotFoundException Pedido não encontrado
     */
    @Transactional
    public OrderIngredientResponseDTO addIngredient(OrderIngredientDTO orderIngredientDTO) throws RecordNotFoundException, BusinessException {
        if (orderIngredientDTO.ingredientList().isEmpty()) {
            throw new BusinessException("É necessário informar os ingredientes que deseja incluir.");
        }

        Order order = orderService.getOrderByIdEntity(orderIngredientDTO.orderId());
        if (order.getStatus().compareTo(Status.REALIZADO) == 0) {
            throw new BusinessException("Não é possível adicionar ingredientes a um pedido realizado.");
        }

        Set<Ingredient> ingredients = new HashSet<>();

        for (Long ingredientId : orderIngredientDTO.ingredientList()) {
            if (this.existsByOrderIdAndIngredientId(order.getId(), ingredientId)) {
                continue;
            }

            Ingredient ingredient = ingredientService.getIngredientEntity(ingredientId);

            OrderIngredient orderIngredient = OrderIngredient.builder()
                    .quantity(orderIngredientDTO.quantity() == null
                            ? Integer.valueOf(1)
                            : orderIngredientDTO.quantity())
                    .id(OrderIngredient.OrderIngredientId.builder()
                            .order(order)
                            .ingredient(ingredient)
                            .build())
                    .build();

            ingredients.add(ingredient);

            orderIngredientRepository.save(orderIngredient);
        }

        if (ingredients.isEmpty()) {
            throw new BusinessException("Nenhum ingrediente adicionado.");
        }

        return new OrderIngredientResponseDTO(order.getId(), ingredients);
    }
    
    // TODO -> implementar método que atualiza a quantidade de um ingrediente em um pedido

}
