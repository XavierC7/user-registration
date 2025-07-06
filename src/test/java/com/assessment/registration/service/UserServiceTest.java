package com.assessment.registration.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.assessment.registration.dto.PhoneDTO;
import com.assessment.registration.dto.UserDTO;
import com.assessment.registration.dto.UserResponseDTO;
import com.assessment.registration.entity.User;
import com.assessment.registration.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;

	@Test
	void registerUserSuccess()

	{
		UserDTO user = new UserDTO();
		user.setName("Xaier");
		user.setEmail("xa@gmail.com");
		user.setPassword("Abc12");

		PhoneDTO phone = new PhoneDTO();
		phone.setNumber("987523872");
		phone.setCityCode("10");
		phone.setCountryCode("57");

		user.setPhones(Collections.singletonList(phone));

		UUID generatedId = UUID.randomUUID();

		when(userRepository.existsByEmail("xa@gmail.com")).thenReturn(false);
		when(userRepository.save(any(User.class))).thenAnswer(inv -> {
			User userRes = inv.getArgument(0);
			userRes.setId(generatedId);
			return userRes;
		});

		UserResponseDTO response = userService.registerUser(user);

		assertNotNull(response);
		assertEquals(generatedId, response.getId());
		assertTrue(response.isActive());
		assertNotNull(response.getToken());

		verify(userRepository).save(any(User.class));

	}

	@Test
	void exceptionIfEmailExists() {

		UserDTO dto = new UserDTO();
		dto.setEmail("juan@test.com");

		when(userRepository.existsByEmail("juan@test.com")).thenReturn(true);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.registerUser(dto);
		});

		assertEquals("The email is already registered.", exception.getMessage());
		verify(userRepository, never()).save(any());
	}
	
	@Test
    void registerUserWithoutPhones() {
        UserDTO user = new UserDTO();
        user.setName("Juan");
        user.setEmail("juan@test.com");
        user.setPassword("Abc12");
        user.setPhones(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("You must provide at least one phone number.", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
	
	@Test
    void unexpectedException() {
        UserDTO user = new UserDTO();
        user.setName("Juan");
        user.setEmail("juan@test.com");
        user.setPassword("Abc12");
        
        PhoneDTO phone = new PhoneDTO();
		phone.setNumber("987523872");
		phone.setCityCode("10");
		phone.setCountryCode("57");

		user.setPhones(Collections.singletonList(phone));

		
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("DB error", ex.getMessage());
    }


}
