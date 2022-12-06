package com.main.neosoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.dto.UserRequest;
import com.main.neosoft.dto.UserResponse;
import com.main.neosoft.util.JwtUtil;

@RestController
public class LoginController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String loginCheck() {
		return "User Logged in Successfully";
	}

//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserCredentials userCredentials){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//        		userCredentials.getUsername(), userCredentials.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!!", HttpStatus.OK);
//    }

	@PostMapping("/login")
	public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody UserRequest userRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("Invalid Username/Password");
		}

		String token = jwtUtil.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token, "Token Generated Successfully"));
	}
}
