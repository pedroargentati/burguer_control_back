package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Event;

import java.time.LocalDate;

public record EventDTO(
        Long id,
        String name,
        LocalDate eventDate
) {
    public EventDTO(Event event) {
        this(event.getId(), event.getName(), event.getEventDate());
    }
}
