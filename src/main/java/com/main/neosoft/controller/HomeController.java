package com.main.neosoft.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.entity.UserContactDetails;
import com.main.neosoft.entity.UserCredentials;
import com.main.neosoft.entity.UserDetails;
import com.main.neosoft.exceptionhandling.PasswordInvalidException;
import com.main.neosoft.exceptionhandling.ResourceNotFoundException;
import com.main.neosoft.repository.UserContactDetailsRepository;
import com.main.neosoft.repository.UserCredentialsRepository;
import com.main.neosoft.repository.UserDetailsRepository;

@RestController
public class HomeController {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Autowired
	UserContactDetailsRepository userContactRepo;

	@Autowired
	UserCredentialsRepository userCredRepo;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDetails userDetails) {

		if (userDetailsRepo.existsByFirstNameAndLastNameAndEmail(userDetails.getFirstName(), userDetails.getLastName(),
				userDetails.getEmail())) {
			return new ResponseEntity<>("User is already exists!", HttpStatus.BAD_REQUEST);
		}
		
//		List<UserDetails> users = userDetailsRepo.findAll();
//		for (UserDetails user:users) {
//			if (user.equals(userDetails)) {
//				return new ResponseEntity<>("User is already exists!", HttpStatus.BAD_REQUEST);
//			}
//		}

		if (userDetailsRepo.existsByEmail(userDetails.getEmail())) {
			return new ResponseEntity<>("Email is already exists!", HttpStatus.BAD_REQUEST);
		}

		Optional<UserCredentials> username = userCredRepo
				.findByUsername(userDetails.getUserCredentials().getUsername());

		if (username.isPresent()) {
			return new ResponseEntity<>("Username is already exists!", HttpStatus.BAD_REQUEST);
		}

//		Set<UserContactDetails> setContacts = userDetails.getUserContactDetails();
//		String spacesAndHyphenRegex = "^(\\d{3}[- ]?){2}\\d{4}$";
//		for (UserContactDetails mobNo : setContacts) {
//			//UserContactDetails contactDetails = new UserContactDetails();
//			if(!mobNo.getMobileNumber().matches(spacesAndHyphenRegex)) {
//				throw new MobileNumberFormatException("Enter Correct Mobile number format");
//			}
//			System.out.println(mobNo.getMobileNumber());
//		}

		if (userDetails.getUserCredentials().getPassword().length() > 15
				|| userDetails.getUserCredentials().getPassword().length() < 6) {
			throw new PasswordInvalidException("Password must be less than 15 and more than 6 characters in length.");
		}

		String upperCaseChars = "(.*[A-Z].*)";
		if (!userDetails.getUserCredentials().getPassword().matches(upperCaseChars)) {
			throw new PasswordInvalidException("Password must have atleast one uppercase character");
		}

		String lowerCaseChars = "(.*[a-z].*)";
		if (!userDetails.getUserCredentials().getPassword().matches(lowerCaseChars)) {
			throw new PasswordInvalidException("Password must have atleast one lowercase character");
		}

		String numbers = "(.*[0-9].*)";
		if (!userDetails.getUserCredentials().getPassword().matches(numbers)) {
			throw new PasswordInvalidException("Password must have atleast one number");
		}

		String specialChars = "(.*[@,#,$,%].*$)";
		if (!userDetails.getUserCredentials().getPassword().matches(specialChars)) {
			throw new PasswordInvalidException("Password must have atleast one special character among @#$%");
		}

		userDetails.getUserCredentials().setPassword(pwdEncoder.encode(userDetails.getUserCredentials().getPassword()));
		userDetailsRepo.save(userDetails);

		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDetails>> searchUsers(@RequestParam("query") String query) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.searchUserDetails(query).forEach(userDetails::add);

		if (userDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/search/allUsers")
	public ResponseEntity<List<UserDetails>> getAllUsers() {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();

		userDetailsRepo.findAll().forEach(userDetails::add);

		if (userDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/search/{userId}")
	public ResponseEntity<UserDetails> getUserById(@PathVariable("userId") Long id) {
		UserDetails userDetails = userDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with id = " + id));

		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDetails> updateUser(@PathVariable("userId") Long id, @Valid @RequestBody UserDetails ud) {
		UserDetails userDetails = userDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with id = " + id));

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
	public ResponseEntity<List<UserDetails>> deleteUserById(@PathVariable("userId") Long id) {

		if (!userDetailsRepo.existsById(id)) {
			throw new ResourceNotFoundException("User Not Found with id = " + id);
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
	public ResponseEntity<List<UserDetails>> searchUserByFirstName(@PathVariable("firstName") String firstName) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByFirstName(firstName).forEach(userDetails::add);

		if (userDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/search/byLastName/{lastName}")
	public ResponseEntity<List<UserDetails>> searchUserByLastName(@PathVariable("lastName") String lastName) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByLastName(lastName).forEach(userDetails::add);

		if (userDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/search/byEmail/{email}")
	public ResponseEntity<List<UserDetails>> searchUserByEmail(@PathVariable("email") String email) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetailsRepo.findByEmail(email).forEach(userDetails::add);

		if (userDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping("/search/byCity/{cityName}")
	public ResponseEntity<List<UserContactDetails>> searchUserByCity(@PathVariable("cityName") String cityName) {
		List<UserContactDetails> userContactDetails = new ArrayList<UserContactDetails>();
		userContactRepo.findByCityName(cityName).forEach(userContactDetails::add);

		if (userContactDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userContactDetails, HttpStatus.OK);
	}

}
