package br.com.argentati.burguer.model.dto;

import java.time.LocalDate;

public record EventDTO(
        Long id,
        String name,
        LocalDate eventDate
) {
}
