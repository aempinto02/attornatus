package br.com.attornatus.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressException extends RuntimeException {

	private static final long serialVersionUID = 5339710258617874574L;

	private final String code;

	public AddressException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
