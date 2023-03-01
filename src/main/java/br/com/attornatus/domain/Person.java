package br.com.attornatus.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Column(unique = true)
	private String document;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthday;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Address> address;

	public Person personUpdate(Person person) {
		this.name = person.getName();
		this.document = person.getDocument();
		this.birthday = person.getBirthday();
		return null;
	}
}
