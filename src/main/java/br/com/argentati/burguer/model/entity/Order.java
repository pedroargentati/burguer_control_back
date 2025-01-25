package br.com.argentati.burguer.model.entity;

import br.com.argentati.burguer.converter.MeatDonenessConverter;
import br.com.argentati.burguer.enums.MeatDoneness;
import br.com.argentati.burguer.enums.Status;
import br.com.argentati.burguer.model.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "meat_doneness", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = MeatDonenessConverter.class)
    private MeatDoneness meatDoneness;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void update(OrderDTO order) {
        if (order.meatDoneness() != null) {
            this.meatDoneness = MeatDoneness.fromString(order.meatDoneness());
        }
        if (order.notes() != null) {
            this.notes = order.notes();
        }

        if (order.status() != null) {
            this.status = Status.fromString(order.status());
        }
    }

}
