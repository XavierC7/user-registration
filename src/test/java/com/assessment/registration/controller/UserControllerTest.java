package com.assessment.registration.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.assessment.registration.dto.UserDTO;
import com.assessment.registration.dto.UserResponseDTO;
import com.assessment.registration.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	void registerUserSuccess() throws Exception {
		UserDTO user = new UserDTO();
		user.setName("Test");
		user.setEmail("test@example.com");
		user.setPassword("Abc12");

		UserResponseDTO response = UserResponseDTO.builder().id(UUID.randomUUID()).token("jwt-token").isActive(true)
				.created(LocalDateTime.now()).modified(LocalDateTime.now()).lastLogin(LocalDateTime.now()).build();

		when(userService.registerUser(any(UserDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
		        .andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").value("jwt-token"))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.active").value(true));
	}
	
	@Test
	void invalidEmail() throws Exception {
	    UserDTO user = new UserDTO();
	    user.setName("Test");
	    user.setEmail("test.cl");
	    user.setPassword("1234");

	    mockMvc.perform(post("/api/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(user)))
	            .andExpect(status().isBadRequest());
	}
	

}
