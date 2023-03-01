package br.com.attornatus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatus.domain.Address;
import br.com.attornatus.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService service;

	@GetMapping
	public ResponseEntity<List<Address>> find(@RequestParam(name = "document", required = false) String document) {
		return ResponseEntity.ok(service.findAllByDocument(document));
	}

	@GetMapping(path = "/main")
	public ResponseEntity<Address> findMain(@RequestParam(name = "document", required = false) String document) {
		return ResponseEntity.ok(service.findByMain(document));
	}

	@PostMapping
	public ResponseEntity<Address> create(@RequestParam(name = "document", required = false) String document,
			@RequestBody Address address) {
		return ResponseEntity.ok(service.save(document, address));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Address> updateMainAddress(@RequestParam(name = "document", required = false) String document,
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.changeMainAddress(id, document));
	}

}
