package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Person;

import java.time.LocalDateTime;

public record PersonDTO(
        Long id,
        String name
) {
    public PersonDTO(Person person) {
        this(person.getId(), person.getName());
    }

    public Person toEntity() {
        return new Person(id(), name(), LocalDateTime.now());
    }

}