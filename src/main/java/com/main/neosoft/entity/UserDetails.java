package com.main.neosoft.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dob;
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private Set<UserContactDetails> userContactDetails = new HashSet<UserContactDetails>();
	
	//@OneToOne(cascade = CascadeType.ALL, mappedBy = "userDetails")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userCredentialId")
	private UserCredentials userCredentials;

}
