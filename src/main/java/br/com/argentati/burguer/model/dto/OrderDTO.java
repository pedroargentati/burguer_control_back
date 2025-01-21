package br.com.argentati.burguer.model.dto;

public record OrderDTO(
        Long id,
        String personName,
        String eventName,
        String meatDoneness,
        String notes
) {
}
