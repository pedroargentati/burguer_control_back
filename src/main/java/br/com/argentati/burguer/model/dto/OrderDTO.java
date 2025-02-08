package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Order;

public record OrderDTO(
        Long id,
        Long personId,
        String personName,
        Long eventId,
        String meatDoneness,
        String notes,
        String status
) {
    public OrderDTO(Order order) {
        this(
                order.getId(),
                order.getPerson() != null ? order.getPerson().getId() : null,
                order.getPerson() != null ? order.getPerson().getName() : null,
                order.getEvent() != null ? order.getEvent().getId() : null,
                order.getMeatDoneness() != null ? order.getMeatDoneness().toString() : null,
                order.getNotes(),
                order.getStatus().toString()
        );
    }

}
