package br.com.argentati.burguer.model.dto;

import br.com.argentati.burguer.model.entity.Person;

public record PersonDTO(
        Long id,
        String name
) {
    public PersonDTO(Person person) {
        this(person.getId(), person.getName());
    }
}