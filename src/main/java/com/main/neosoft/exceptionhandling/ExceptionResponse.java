package com.main.neosoft.exceptionhandling;

import java.util.Date;

import lombok.Data;

@Data
public class ExceptionResponse {

	  private int statusCode;
	  private Date timestamp;
	  private String message;
	  private String description;
}
