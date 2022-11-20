package com.main.neosoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.neosoft.entity.UserContactDetails;

@Repository
public interface UserContactDetailsRepository extends JpaRepository<UserContactDetails, Long>{

	List<UserContactDetails> findByUserContactId(Long userContactId);
	
	List<UserContactDetails> findByCityName(String cityName);
}
