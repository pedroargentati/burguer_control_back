package br.com.argentati.burguer.service;

import br.com.argentati.burguer.exception.BusinessException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.IngredientDTO;
import br.com.argentati.burguer.model.entity.Ingredient;
import br.com.argentati.burguer.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Obtém os ingredientes
     *
     * @param pageable Paginação
     * @return Ingredientes
     */
    public Page<IngredientDTO> getIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable)
                .map(IngredientDTO::new);
    }

    /**
     * Obtém um ingrediente
     *
     * @param id ID do ingrediente
     * @return Ingrediente
     */
    public IngredientDTO getIngredient(Long id) {
        return ingredientRepository.findById(id)
                .map(IngredientDTO::new)
                .orElse(null);
    }

    /**
     * Obtém um ingrediente
     *
     * @param id ID do ingrediente
     * @return Ingrediente
     * @throws RecordNotFoundException Ingrediente não encontrado
     */
    public Ingredient getIngredientEntity(Long id) throws RecordNotFoundException {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingrediente não encontrado"));
    }

    /**
     * Obtém um ingrediente pelo nome
     *
     * @param name Nome do ingrediente
     * @return Ingrediente
     */
    public IngredientDTO getIngredientByName(String name) {
        return ingredientRepository.findFirstByName(name)
                .map(IngredientDTO::new)
                .orElse(null);
    }

    /**
     * Adiciona um ingrediente
     *
     * @param ingredientDTO Ingrediente
     * @return Ingrediente
     * @throws BusinessException Ingrediente já cadastrado
     */
    @Transactional
    public IngredientDTO createIngredient(IngredientDTO ingredientDTO) throws BusinessException {
        IngredientDTO ingredient = this.getIngredientByName(ingredientDTO.name());

        Boolean byPass = ingredientDTO.byPass() == null
                ? Boolean.FALSE
                : ingredientDTO.byPass();

        if (ingredient != null && byPass == Boolean.FALSE) {
            throw new BusinessException(String.format("Ingrediente %s já cadastrado. Deseja realmente incluir outro com este mesmo nome ?", ingredientDTO.name()));
        }

        Ingredient ingredientEntity = Ingredient.builder()
                .name(ingredientDTO.name())
                .category(ingredientDTO.category())
                .createdAt(LocalDateTime.now())
                .build();

        return new IngredientDTO(ingredientRepository.save(ingredientEntity));
    }

    /**
     * Atualiza um ingrediente
     *
     * @param ingredientDTO Ingrediente
     * @return Ingrediente
     * @throws RecordNotFoundException Ingrediente não encontrado
     */
    @Transactional
    public IngredientDTO updateIngredient(IngredientDTO ingredientDTO) throws RecordNotFoundException {
        Ingredient ingredient = this.getIngredientEntity(ingredientDTO.id());

        ingredient.update(ingredientDTO);

        return new IngredientDTO(ingredientRepository.save(ingredient));
    }

    /**
     * Remove um ingrediente
     *
     * @param id ID do ingrediente
     * @throws RecordNotFoundException Ingrediente não encontrado
     */
    @Transactional
    public IngredientDTO removeIngredient(Long id) throws RecordNotFoundException {
        Ingredient ingredient = this.getIngredientEntity(id);

        ingredientRepository.delete(ingredient);

        return new IngredientDTO(ingredient);
    }

}
