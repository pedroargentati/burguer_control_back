package br.com.argentati.burguer.controller;

import br.com.argentati.burguer.common.RestCommonService;
import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.PersonDTO;
import br.com.argentati.burguer.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController extends RestCommonService {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) throws RecordNotFoundException {
        return super.buildResponseForEntity(personService.getPersonById(id));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO eventDTO) {
        return super.buildResponseForPost(personService.createPerson(eventDTO), eventDTO.id());
    }

    @PutMapping
    public ResponseEntity<PersonDTO> updatePerson(@Valid @RequestBody PersonDTO eventDTO) throws RecordNotFoundException {
        return super.buildResponseForEntity(personService.updatePerson(eventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDTO> deletePerson(@PathVariable Long id) throws RecordNotFoundException {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}
