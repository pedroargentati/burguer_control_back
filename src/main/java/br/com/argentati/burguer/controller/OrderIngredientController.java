package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.exception.BusinessException;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.OrderIngredientDTO;
import br.com.argentati.burguer.model.dto.response.OrderIngredientResponseDTO;
import br.com.argentati.burguer.model.entity.Ingredient;
import br.com.argentati.burguer.service.OrderIngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-ingredient")
@RequiredArgsConstructor
public class OrderIngredientController extends RestCommonService {

    private final OrderIngredientService orderIngredientService;

    @PostMapping
    public ResponseEntity<OrderIngredientResponseDTO> addIngredient(@Valid @RequestBody OrderIngredientDTO orderIngredientDTO) throws RecordNotFoundException, BusinessException {
        var orderIngredient = orderIngredientService.addIngredient(orderIngredientDTO);

        return super.buildResponseForPost(orderIngredient, orderIngredientDTO.orderId());
    }

}
