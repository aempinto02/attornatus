package br.com.attornatus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatus.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	public Optional<Person> findByDocument(String document);
}
