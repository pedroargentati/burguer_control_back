package br.com.argentati.burguer.model.entity;

import br.com.argentati.burguer.converter.MeatDonenessConverter;
import br.com.argentati.burguer.enums.MeatDoneness;
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
