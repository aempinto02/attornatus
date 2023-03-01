package br.com.attornatus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.attornatus.domain.Person;
import br.com.attornatus.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public PersonService(PersonRepository repository) {
		this.repository = repository;
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

	public Person save(Person person) {
		if (!person.getAddress().isEmpty()) {
			person.getAddress().get(0).setMain(true);
		}

		return repository.save(person);
	}

	public Person save(String document, Person person) {
		Person personToUpdate = repository.findByDocument(document)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
		personToUpdate.personUpdate(person);
		return repository.save(personToUpdate);
	}

	public List<Person> findByDocument(String document) {
		return List.of(repository.findByDocument(document)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!")));
	}

}
