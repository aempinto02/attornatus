package br.com.attornatus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.attornatus.domain.Address;
import br.com.attornatus.domain.Person;
import br.com.attornatus.exception.AddressException;
import br.com.attornatus.repository.AddressRepository;
import br.com.attornatus.repository.PersonRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	@Autowired
	private PersonRepository personRepository;

	public AddressService(AddressRepository repository, PersonRepository personRepository) {
		this.repository = repository;
		this.personRepository = personRepository;
	}

	public List<Address> findAllByDocument(String document) {
		Person person = personRepository.findByDocument(document)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

		return person.getAddress();
	}

	public Address findByMain(String document) {
		List<Address> addressList = getAddressListByPerson(document);

		Optional<Address> result = addressList.stream().filter(Address::isMain).findFirst();
		return result.orElse(null);
	}

	public Address changeMainAddress(Long id, String document) {
		Address oldMainAddress = findByMain(document);
		oldMainAddress.setMain(false);
		repository.save(oldMainAddress);

		List<Address> addressList = getAddressListByPerson(document);
		Optional<Address> result = addressList.stream().filter(address -> address.getId().equals(id)).findFirst();
		Address newMainAddress = result.orElseThrow(() -> new AddressException("Address not found!", "400"));
		newMainAddress.setMain(true);
		return repository.save(newMainAddress);
	}

	public Address save(String document, Address address) {
		Person person = personRepository.findByDocument(document)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

		List<Address> personAddressList = person.getAddress();

		address.setMain(false);

		if (personAddressList.isEmpty()) {
			address.setMain(true);
		}

		Address addressToAdd = repository.save(address);
		person.getAddress().add(addressToAdd);
		personRepository.save(person);

		return addressToAdd;
	}

	private List<Address> getAddressListByPerson(String document) {
		Person person = personRepository.findByDocument(document)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

		return person.getAddress();
	}

}
