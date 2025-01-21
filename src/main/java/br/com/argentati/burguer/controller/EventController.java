package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.EventDTO;
import br.com.argentati.burguer.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController extends RestCommonService {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) throws RecordNotFoundException {
        return super.buildResponseForEntity(eventService.getEvent(id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        return super.buildResponseForPost(eventService.createEvent(eventDTO), eventDTO.id());
    }

    @PutMapping
    public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDTO) throws RecordNotFoundException {
        return super.buildResponseForEntity(eventService.updateEvent(eventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable Long id) throws RecordNotFoundException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
