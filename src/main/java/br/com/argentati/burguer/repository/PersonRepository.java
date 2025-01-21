package br.com.argentati.burguer.repository;

import br.com.argentati.burguer.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
