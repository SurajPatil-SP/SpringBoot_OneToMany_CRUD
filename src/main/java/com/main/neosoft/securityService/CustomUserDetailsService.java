package com.main.neosoft.securityService;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.neosoft.entity.UserCredentials;
import com.main.neosoft.repository.UserCredentialsRepository;
import com.main.neosoft.securityImpl.CustomUserDetailsServiceImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserCredentialsRepository userCredRepo;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserCredentials userCredentials = userCredRepo.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with Username: " +username));
//
//		return new CustomUserDetailsServiceImpl(userCredentials);
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCredentials userCredentials = userCredRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with Username: " + username));

		return new User(userCredentials.getUsername(), userCredentials.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(userCredentials.getRole())));
	}
}
