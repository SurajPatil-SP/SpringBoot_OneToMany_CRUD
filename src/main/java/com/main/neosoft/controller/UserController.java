package com.main.neosoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.entity.UserDetails;
import com.main.neosoft.repository.UserDetailsRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	private static final int PAGE_SIZE = 2;

	@GetMapping
	public List<UserDetails> getUsers(@RequestParam(defaultValue = "0") int page) {
		// Logic to retrieve users from a data source
		List<UserDetails> allUsers = userDetailsRepo.findAll();

		// Calculate the starting and ending index for the current page
		int startIndex = page * PAGE_SIZE;
		int endIndex = Math.min(startIndex + PAGE_SIZE, allUsers.size());

		// Extract the users for the current page
		List<UserDetails> currentPageUsers = allUsers.subList(startIndex, endIndex);

		return currentPageUsers;
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<Page<UserDetails>> getAllUsers(Pageable pageable, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		Page<UserDetails> users = userDetailsRepo.findAll(pageable);
		return ResponseEntity.ok(users);
	}
}
