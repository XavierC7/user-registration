package com.assessment.registration.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.assessment.registration.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByEmail(String email);
	
	

}
