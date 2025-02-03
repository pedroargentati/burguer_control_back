package br.com.argentati.burguer.service;

import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.EventDTO;
import br.com.argentati.burguer.model.entity.Event;
import br.com.argentati.burguer.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Obtém os eventos.
     * @param pageable Paginação.
     * @return Os eventos.
     */
    public Page<EventDTO> getEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(EventDTO::new);
    }

    /**
     * Obtém um evento pelo id.
     * @param id Id do evento.
     * @return O evento.
     * @throws RecordNotFoundException Se o evento não for encontrado.
     */
    public Event getEntityEvent(Long id) throws RecordNotFoundException {
       return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Evento não encontrado."));
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

    /**
     * Atualiza um evento.
     * @param eventDTO Dados do evento.
     * @return O evento atualizado.
     * @throws RecordNotFoundException Se o evento não for encontrado.
     */
    @Transactional
    public EventDTO updateEvent(EventDTO eventDTO) throws RecordNotFoundException {
        Event event = this.getEntityEvent(eventDTO.id());

        event.update(eventDTO);

        return new EventDTO(eventRepository.save(event));
    }

    /**
     * Deleta um evento.
     * @param id Id do evento.
     * @throws RecordNotFoundException Se o evento não for encontrado.
     */
    @Transactional
    public EventDTO deleteEvent(Long id) throws RecordNotFoundException {
        Event event = this.getEntityEvent(id);

        eventRepository.delete(event);

        return new EventDTO(event);
    }

}
