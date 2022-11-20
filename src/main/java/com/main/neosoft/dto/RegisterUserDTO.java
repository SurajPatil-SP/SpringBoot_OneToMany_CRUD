package com.main.neosoft.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RegisterUserDTO {
	
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dob;
	private String cityName;
	private String mobileNumber;
	private String username;
	private String password;

}
