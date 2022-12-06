package com.main.neosoft.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@NotBlank(message = "The city is required.")
	private String cityName;

	@NotNull
	@Size(min = 10, max = 12, message = "Mobile Number Should be 10 Digit")
	@Pattern(regexp = "(0|91)?[6-9][0-9]{9}")
	private String mobileNumber;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
//	@JoinColumn(name = "user_Id")
//	private UserDetails userDetails;

}
