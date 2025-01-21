package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
