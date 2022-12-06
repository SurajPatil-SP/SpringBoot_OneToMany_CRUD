package com.main.neosoft.exceptionhandling;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ExceptionResponse handleResourceNotFound(ResourceNotFoundException exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

	@ExceptionHandler(PasswordInvalidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleInvalidPassword(PasswordInvalidException exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

	@ExceptionHandler(MobileNumberFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleMobileNumberFormat(MobileNumberFormatException exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ExceptionResponse handleInvalidLoginCredentials(InvalidCredentialsException exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse globalExceptionHandler(Exception exception, WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			WebRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		error.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage());
		error.setDescription(request.getDescription(false));
		return error;
	}

}
