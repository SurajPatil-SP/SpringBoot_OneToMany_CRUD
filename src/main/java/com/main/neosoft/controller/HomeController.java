package com.main.neosoft.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.entity.UserContactDetails;
import com.main.neosoft.entity.UserDetails;
import com.main.neosoft.exceptionhandling.ResourceNotFoundException;
import com.main.neosoft.repository.UserContactDetailsRepository;
import com.main.neosoft.repository.UserCredentialsRepository;
import com.main.neosoft.repository.UserDetailsRepository;
import com.sipios.springsearch.anotation.SearchSpec;

@RestController
public class HomeController {
	
	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	@Autowired
	UserContactDetailsRepository userContactRepo;
	
	@Autowired
	UserCredentialsRepository userCredRepo;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser (@RequestBody UserDetails ud) {
		
		if (userDetailsRepo.existsByEmail(ud.getEmail())) {
			return new ResponseEntity<>("Email is already exists!", HttpStatus.BAD_REQUEST);
		}
		
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(ud.getFirstName());
		userDetails.setLastName(ud.getLastName());
		userDetails.setEmail(ud.getEmail());
		userDetails.setDob(ud.getDob());
		userDetails.setUserContactDetails(ud.getUserContactDetails());
		userDetails.setUserCredentials(ud.getUserCredentials());
		
		userDetailsRepo.save(userDetails);
		
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}
	
	 @GetMapping("/search")
	 public ResponseEntity<List<UserDetails>> searchUsers(@RequestParam("query") String query){
		 List<UserDetails> userDetails = new ArrayList<UserDetails>();
		 userDetailsRepo.searchUserDetails(query).forEach(userDetails::add);
		 
		 if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	 }
	 
	 @GetMapping("/searchUsers")
	 public ResponseEntity<List<UserDetails>> searchForUsers(@SearchSpec Specification<UserDetails> specs){
		 return new ResponseEntity<>(userDetailsRepo.findAll(Specification.where(specs)), HttpStatus.OK);
	 }
	
	@GetMapping("/search/allUsers")
	public ResponseEntity<List<UserDetails>> getAllUsers () {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		
		userDetailsRepo.findAll().forEach(userDetails::add);
		
		if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@GetMapping("/search/{userId}")
	public ResponseEntity<UserDetails> getUserById (@PathVariable("userId") Long id) {
		UserDetails userDetails = userDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with id = " +id));
		
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDetails> updateUser (@PathVariable("userId") Long id, @RequestBody UserDetails ud) {
		UserDetails userDetails = userDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with id = " +id));
		
		userDetails.setFirstName(ud.getFirstName());
		userDetails.setLastName(ud.getLastName());
		userDetails.setEmail(ud.getEmail());
		userDetails.setDob(ud.getDob());
		userDetails.setUserContactDetails(ud.getUserContactDetails());
		userDetails.setUserCredentials(ud.getUserCredentials());
		
		return new ResponseEntity<>(userDetailsRepo.save(userDetails), HttpStatus.OK);
	}
	
//	@DeleteMapping("/delete/{userId}")
//	public ResponseEntity<?> deleteUserById (@PathVariable("userId") Long id) {
//		userDetailsRepo.deleteById(id);
//		return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
//	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<List<UserDetails>> deleteUserById (@PathVariable("userId") Long id) {
		
		if(!userDetailsRepo.existsById(id)) {
			throw new ResourceNotFoundException("User Not Found with id = " +id);
		}
		
		userDetailsRepo.deleteById(id);
		return new ResponseEntity<>(userDetailsRepo.findAll(), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/All")
	public ResponseEntity<String> deleteAllUsers() {
		userDetailsRepo.deleteAll();
		return new ResponseEntity<>("All Users Deleted Successfully", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/search/byFirstName/{firstName}")
	public ResponseEntity<List<UserDetails>> searchUserByFirstName (@PathVariable("firstName") String firstName) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByFirstName(firstName).forEach(userDetails::add);
		
		if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@GetMapping("/search/byLastName/{lastName}")
	public ResponseEntity<List<UserDetails>> searchUserByLastName (@PathVariable("lastName") String lastName) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByLastName(lastName).forEach(userDetails::add);
		
		if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@GetMapping("/search/byEmail/{email}")
	public ResponseEntity<List<UserDetails>> searchUserByEmail (@PathVariable("email") String email) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByEmail(email).forEach(userDetails::add);
		
		if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@GetMapping("/search/byCity/{cityName}")
	public ResponseEntity<List<UserContactDetails>> searchUserByCity (@PathVariable("cityName") String cityName) {
		List<UserContactDetails> userContactDetails = new ArrayList<UserContactDetails>();
		userContactRepo.findByCityName(cityName).forEach(userContactDetails::add);
		
		if(userContactDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userContactDetails, HttpStatus.OK);
	}

}
