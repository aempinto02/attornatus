package br.com.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatus.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
