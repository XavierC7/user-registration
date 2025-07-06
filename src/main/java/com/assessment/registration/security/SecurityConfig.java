package com.assessment.registration.security;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityConfig {
	
	public static String encodePassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

  
}
