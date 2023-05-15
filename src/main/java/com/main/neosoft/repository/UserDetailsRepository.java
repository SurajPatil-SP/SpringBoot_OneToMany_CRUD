package com.main.neosoft.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.neosoft.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	Page<UserDetails> findAll(Pageable pageable);

	Boolean existsByEmail(String email);

	Boolean existsByFirstName(String firstName);

	Boolean existsByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);

	Boolean existsByLastName(String lastName);

	List<UserDetails> findByFirstName(String firstName);

	List<UserDetails> findByLastName(String lastName);

	List<UserDetails> findByEmail(String email);

//	 @Query("SELECT p FROM UserDetails p WHERE " +
//	            "p.firstName LIKE CONCAT('%',:query, '%')" +
//	            "Or p.lastName LIKE CONCAT('%',:query, '%')" +
//	            "Or p.email LIKE CONCAT('%', :query, '%')")
//	List<UserDetails> searchUserDetails(String query);

	@Query("SELECT p FROM UserDetails p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email) LIKE %?1%")
	List<UserDetails> searchUserDetails(String query);
}
