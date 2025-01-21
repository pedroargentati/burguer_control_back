package br.com.argentati.burguer.service;

import br.com.argentati.burguer.exception.RecordNotFoundException;
import br.com.argentati.burguer.model.dto.PersonDTO;
import br.com.argentati.burguer.model.entity.Person;
import br.com.argentati.burguer.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Obtém uma pessoa pelo id.
     * @param id Id da pessoa.
     * @return A pessoa.
     * @throws RecordNotFoundException Se a pessoa não for encontrada.
     */
    public Person getEntityPerson(Long id) throws RecordNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Pessoa não encontrada."));
    }

    /**
     * Obtém uma pessoa pelo id.
     * @param id Id da pessoa.
     * @return A pessoa.
     */
    public PersonDTO getPersonById(Long id) {
        return personRepository.findById(id)
                .map(PersonDTO::new)
                .orElse(null);
    }

    /**
     * Cria uma pessoa.
     * @param personDTO Dados da pessoa.
     * @return A pessoa criada.
     */
    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) {
        return new PersonDTO(personRepository.save(personDTO.toEntity()));
    }

    /**
     * Atualiza uma pessoa.
     * @param personDTO Dados da pessoa.
     * @return A pessoa atualizada.
     * @throws RecordNotFoundException Se a pessoa não for encontrada.
     */
    @Transactional
    public PersonDTO updatePerson(PersonDTO personDTO) throws RecordNotFoundException {
        Person person = this.getEntityPerson(personDTO.id());

        person.update(personDTO);

        return new PersonDTO(personRepository.save(person));
    }

    /**
     * Remove uma pessoa.
     * @param id Id da pessoa.
     * @throws RecordNotFoundException Se a pessoa não for encontrada.
     */
    @Transactional
    public void deletePerson(Long id) throws RecordNotFoundException {
        Person person = this.getEntityPerson(id);

        personRepository.delete(person);
    }

}
