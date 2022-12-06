package com.main.neosoft.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRequest {

	@NotBlank(message = "Username is required.")
	private String username;

	@NotBlank(message = "Password is required.")
	private String password;

}
