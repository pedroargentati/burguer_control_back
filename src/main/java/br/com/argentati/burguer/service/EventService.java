package br.com.argentati.burguer.service;

import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.EventDTO;
import br.com.argentati.burguer.model.entity.Event;
import br.com.argentati.burguer.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Obtém um evento pelo id.
     * @param id Id do evento.
     * @return O evento.
     */
    public EventDTO getEvent(Long id) {
        return eventRepository.findById(id)
                .map(EventDTO::new)
                .orElse(null);
    }

    /**
     * Cria um evento.
     * @param eventDTO Dados do evento.
     * @return O evento criado.
     */
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        return new EventDTO(eventRepository.save(eventDTO.toEntity()));
    }

    @Transactional
    public EventDTO updateEvent(EventDTO eventDTO) throws RecordNotFoundException {
        Event event = eventRepository.findById(eventDTO.id())
                .orElseThrow(() -> new RecordNotFoundException("Evento não encontrado."));

        event.update(eventDTO);

        return new EventDTO(eventRepository.save(event));
    }

}
