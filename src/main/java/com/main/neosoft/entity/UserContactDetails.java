package com.main.neosoft.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userContactId;
	private String cityName;
	private String mobileNumber;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
//	@JoinColumn(name = "user_Id")
//	private UserDetails userDetails;

}
