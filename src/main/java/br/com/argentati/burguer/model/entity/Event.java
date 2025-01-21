package br.com.argentati.burguer.model.entity;

import br.com.argentati.burguer.model.dto.EventDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotNull(message = "O nome do evento é obrigatório.")
    private String name;

    @Column(name = "event_date", nullable = false)
    @NotNull(message = "A data do evento é obrigatória.")
    private LocalDate eventDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void update(EventDTO eventDTO) {
        if (eventDTO.eventDate() != null) {
            this.eventDate = eventDTO.eventDate();
        }

        if (eventDTO.name() != null) {
            this.name = eventDTO.name();
        }
    }

}
