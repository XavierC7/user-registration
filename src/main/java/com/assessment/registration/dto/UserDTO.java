package com.assessment.registration.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {
	
	@NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
    	regexp = "^(?=(?:[^A-Z]*[A-Z][^A-Z]*)$)(?=(?:[^a-z]*[a-z]){2,})(?=(?:[^\\d]*\\d[^\\d]*){2})[A-Za-z\\d]*$",
        message = "Password must contain one uppercase letter, lowercase letters, and two digits"
    )
    private String password;

    private List<PhoneDTO> phones;

}
