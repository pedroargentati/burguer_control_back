package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Order;

public record OrderDTO(
        Long id,
        Long personId,
        Long eventId,
        String meatDoneness,
        String notes
) {
    public OrderDTO(Order order) {
        this(
                order.getId(),
                order.getPerson() != null ? order.getPerson().getId() : null,
                order.getEvent() != null ? order.getEvent().getId() : null,
                order.getMeatDoneness() != null ? order.getMeatDoneness().toString() : null,
                order.getNotes()
        );
    }

}
