package com.main.neosoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.entity.UserContactDetails;
import com.main.neosoft.exceptionhandling.ResourceNotFoundException;
import com.main.neosoft.repository.UserContactDetailsRepository;
import com.main.neosoft.repository.UserDetailsRepository;

@RestController
public class UserContactDetailsController {

	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	@Autowired
	UserContactDetailsRepository userContactRepo;
	
	 
	 @GetMapping("/users/{userId}/contactDetails")
	 public ResponseEntity<List<UserContactDetails>> getAllContactDetailsByUserId (@PathVariable("userId") Long id) {
			if(!userDetailsRepo.existsById(id)) {
				throw new ResourceNotFoundException("User Not Found with id = " +id);
			}
			
			List<UserContactDetails> contactDetails = userContactRepo.findByUserContactId(id);
			return new ResponseEntity<> (contactDetails, HttpStatus.OK);
	 }
}
