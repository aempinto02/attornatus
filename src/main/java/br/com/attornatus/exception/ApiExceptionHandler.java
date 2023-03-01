package br.com.attornatus.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiExceptionResponse> handleException(UsernameNotFoundException exception) {
		ApiExceptionResponse error = new ApiExceptionResponse("404", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AddressException.class)
	public ResponseEntity<ApiExceptionResponse> handleException(AddressException exception) {
		ApiExceptionResponse error = new ApiExceptionResponse("400", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiExceptionResponse> handleException(DataIntegrityViolationException exception) {
		ApiExceptionResponse error = new ApiExceptionResponse("400", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}