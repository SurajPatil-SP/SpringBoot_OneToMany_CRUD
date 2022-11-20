package com.main.neosoft.exceptionhandling;

import java.util.Date;

import org.springframework.http.HttpStatus;
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

}
