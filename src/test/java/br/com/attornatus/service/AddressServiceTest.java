package br.com.attornatus.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import br.com.attornatus.repository.AddressRepository;
import br.com.attornatus.repository.PersonRepository;

public class AddressServiceTest {

	@Mock
	private AddressRepository repositoryMock;

	@Mock
	private PersonRepository personRepositoryMock;

	private AddressService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new AddressService(repositoryMock, personRepositoryMock);
	}

	@Test
	void testFindAllByDocument() {
		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));

		when(personRepositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));

		List<Address> result = service.findAllByDocument("111.222.333-44");

		boolean isNotEmpty = !result.isEmpty();

		assertTrue(isNotEmpty, "Should not be empty list of address");
	}

	@Test
	void testFindByMain() {
		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade")));

		when(personRepositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));

		Address result = service.findByMain("111.222.333-44");

		assertNotNull(result, "Should have the main address");
	}

	@Test
	void testChangeMainAddress() {
		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(),
				List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade"),
						new Address(2L, false, "Rua Teste 2", "11223-000", "51", "Cidade 2"),
						new Address(3L, false, "Rua Teste 3", "54321-000", "121", "Cidade 3")));

		when(personRepositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));
		when(repositoryMock.save(person.getAddress().get(0))).thenReturn(null);
		when(repositoryMock.save(person.getAddress().get(1))).thenReturn(person.getAddress().get(1));

		Address result = service.changeMainAddress(2L, "111.222.333-44");

		assertNotNull(result, "Should have changed the main address");
	}

	@Test
	void testSave() {
		List<Address> addresses = new ArrayList<>();
		addresses.addAll(List.of(new Address(1L, true, "Rua Teste", "12345-000", "75", "Cidade"),
				new Address(2L, false, "Rua Teste 2", "11223-000", "51", "Cidade 2"),
				new Address(3L, false, "Rua Teste 3", "54321-000", "121", "Cidade 3")));

		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(), addresses);

		Address address = new Address(null, false, "Rua Teste 4", "12345-000", "99", "Cidade 4");

		when(personRepositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));
		when(repositoryMock.save(address))
				.thenReturn(new Address(4L, false, "Rua Teste 4", "12345-000", "99", "Cidade 4"));
		when(personRepositoryMock.save(any())).thenReturn(null);

		Address result = service.save("111.222.333-44", address);

		assertNotNull(result, "Should have changed the main address");
	}

	@Test
	void testSaveWithEmptyAddressList() {
		List<Address> addresses = new ArrayList<>();

		Person person = new Person(1L, "Nome Teste", "111.222.333-44", new Date(), addresses);

		Address address = new Address(null, false, "Rua Teste", "12345-000", "99", "Cidade");

		when(personRepositoryMock.findByDocument("111.222.333-44")).thenReturn(Optional.of(person));
		when(repositoryMock.save(address)).thenReturn(new Address(1L, true, "Rua Teste", "12345-000", "99", "Cidade"));
		when(personRepositoryMock.save(any())).thenReturn(null);

		Address result = service.save("111.222.333-44", address);

		assertNotNull(result, "Should have changed the main address");
	}
}
