package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Order;

public record OrderDTO(
        Long id,
        String personName,
        String eventName,
        String meatDoneness,
        String notes
) {
    public OrderDTO(Order order) {
        this(
                order.getId(),
                order.getPerson() != null ? order.getPerson().getName() : null,
                order.getEvent() != null ? order.getEvent().getName() : null,
                order.getMeatDoneness() != null ? order.getMeatDoneness().toString() : null,
                order.getNotes()
        );
    }

}
