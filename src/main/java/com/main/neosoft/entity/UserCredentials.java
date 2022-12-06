package com.main.neosoft.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserCredentials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userCredentialId;

	@NotBlank(message = "Username is required.")
	private String username;

	@NotBlank(message = "Password is required.")
	private String password;

	@NotBlank(message = "User Role is required.")
	private String role;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "user_Id")
//	private UserDetails userDetails;

}
