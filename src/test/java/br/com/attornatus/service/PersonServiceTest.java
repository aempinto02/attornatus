package br.com.attornatus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.attornatus.domain.Address;
import br.com.attornatus.domain.Person;
import br.com.attornatus.repository.PersonRepository;

public class PersonServiceTest {

	@Mock
	private PersonRepository repositoryMock;

	private PersonService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new PersonService(repositoryMock);
	}

	@Test
	void testFindAll() {
		List<Person> personList = new ArrayList<>();
		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));
		Person person1 = new Person(2L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));
		Person person2 = new Person(3L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));
		personList.addAll(List.of(person, person1, person2));

		when(repositoryMock.findAll()).thenReturn(personList);

		List<Person> result = service.findAll();

		assertEquals(3, result.size(), "Should not be different of 3 the list size");
	}

	@Test
	void testSave() {
		Person person = new Person(null, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(null, true, "Rua Teste", "12345-000", "75", "Cidade")));
		Person personSaved = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));

		when(repositoryMock.save(person)).thenReturn(personSaved);

		Person result = service.save(person);

		assertEquals(1L, result.getId(), "Should the id be 1L");
	}

	@Test
	void testSaveWithEmptyAddressList() {
		Person person = new Person(null, "Nome Teste", "111.222.333-44", new Date(), new ArrayList<>());
		Person personSaved = new Person(1L, "Nome Teste", "111.222.333-44", new Date(), new ArrayList<>());

		when(repositoryMock.save(person)).thenReturn(personSaved);

		Person result = service.save(person);

		assertEquals(1L, result.getId(), "Should the id be 1L");
	}

	@Test
	void testSaveWithDocument() {
		Person person = new Person(null, "Nome Teste Update", "111.222.333-55", new Date(),
				List.of(new Address(null, true, "Rua Teste", "12345-000", "75", "Cidade")));
		Person personToUpdate = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));
		Person personSaved = new Person(1L, "Nome Teste Update", "111.222.333-55", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));

		when(repositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(personToUpdate));
		when(repositoryMock.save(personToUpdate)).thenReturn(personSaved);

		Person result = service.save("111.222.333-44", person);

		assertEquals(1L, result.getId(), "Should the id be 1L");
	}

	@Test
	void testFindByDocument() {
		Person person = new Person(1L, "Nome Teste 1", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "12", "Cidade")));

		when(repositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));

		List<Person> result = service.findByDocument("111.222.333-44");

		assertEquals(1L, result.get(0).getId(), "Should the id be 1L");
	}
}
