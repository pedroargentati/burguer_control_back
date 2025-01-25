package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.exception.BusinessException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.IngredientDTO;
import br.com.argentati.burguer.service.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController extends RestCommonService {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<Page<IngredientDTO>> getIngredients(@PageableDefault(size = 15) Pageable pageable) {
        return super.buildDefaultResponseForPage(ingredientService.getIngredients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Long id) throws RecordNotFoundException {
        return super.buildResponseForEntity(ingredientService.getIngredient(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<IngredientDTO> getIngredientByName(@PathVariable String name) throws RecordNotFoundException {
        return super.buildResponseForEntity(ingredientService.getIngredientByName(name));
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) throws BusinessException, RecordNotFoundException {
        return super.buildResponseForEntity(ingredientService.createIngredient(ingredientDTO));
    }

    @PutMapping
    public ResponseEntity<IngredientDTO> updateIngredient(@RequestBody IngredientDTO ingredientDTO) throws BusinessException, RecordNotFoundException {
        return super.buildResponseForEntity(ingredientService.updateIngredient(ingredientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable Long id) throws RecordNotFoundException {
        var ingredient = ingredientService.removeIngredient(id);

        return super.buildResponseForDelete(ingredient);
    }

}
