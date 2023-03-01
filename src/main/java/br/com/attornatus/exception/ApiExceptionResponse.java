package br.com.attornatus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiExceptionResponse {

	private String errorCode;
	private String errorMessage;
}
