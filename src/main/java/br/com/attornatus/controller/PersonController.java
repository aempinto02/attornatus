package br.com.attornatus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatus.domain.Person;
import br.com.attornatus.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService service;

	@GetMapping
	public ResponseEntity<List<Person>> find(@RequestParam(name = "document", required = false) String document) {
		if (document == null || "".equals(document)) {
			return ResponseEntity.ok(service.findAll());
		}

		return ResponseEntity.ok(service.findByDocument(document));
	}

	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person person) {
		return ResponseEntity.ok(service.save(person));
	}

	@PatchMapping
	public ResponseEntity<Person> update(@RequestParam(name = "document", required = false) String document,
			@RequestBody Person person) {
		return ResponseEntity.ok(service.save(document, person));
	}

}
