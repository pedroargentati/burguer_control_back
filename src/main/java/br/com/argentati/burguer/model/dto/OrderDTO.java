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
        this(order.getId(), order.getPerson().getName(), order.getEvent().getName(), order.getMeatDoneness().toString(), order.getNotes());
    }
}
