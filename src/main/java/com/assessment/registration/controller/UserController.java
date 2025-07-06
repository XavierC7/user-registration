package com.assessment.registration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.*;

import com.assessment.registration.dto.UserDTO;
import com.assessment.registration.dto.UserResponseDTO;
import com.assessment.registration.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Creates a user and returns metadata.")
	@ApiResponse(responseCode = "201", description = "User registered successfully")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserDTO dto) {
        UserResponseDTO response = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
