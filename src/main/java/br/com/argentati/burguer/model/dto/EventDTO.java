package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        String name,
        LocalDate eventDate
) {
    public EventDTO(Event event) {
        this(event.getId(), event.getName(), event.getEventDate());
    }

    public Event toEntity() {
        return new Event(id, name, eventDate, LocalDateTime.now());
    }
}
