package com.assessment.registration.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assessment.registration.dto.PhoneDTO;
import com.assessment.registration.dto.UserDTO;
import com.assessment.registration.dto.UserResponseDTO;
import com.assessment.registration.entity.Phone;
import com.assessment.registration.entity.User;
import com.assessment.registration.exception.EmailAlreadyExistsException;
import com.assessment.registration.repository.UserRepository;
import com.assessment.registration.security.JwtTokenProvider;
import com.assessment.registration.security.SecurityConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDTO registerUser(UserDTO dto) {

		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new EmailAlreadyExistsException("The email is already registered.");
		}

		if (dto.getPhones() == null || dto.getPhones().isEmpty()) {
			throw new RuntimeException("You must provide at least one phone number.");
		}

		String token = JwtTokenProvider.generateToken(dto.getEmail());
		String encodedPassword = SecurityConfig.encodePassword(dto.getEmail());

		User user = User.builder().name(dto.getName()).email(dto.getEmail()).password(encodedPassword)
				.created(LocalDateTime.now()).modified(LocalDateTime.now()).lastLogin(LocalDateTime.now())
				.isActive(true).token(token).build();

		if (dto.getPhones() != null) {
			var phones = dto.getPhones().stream().map(phoneDTO -> mapPhone(phoneDTO, user))
					.collect(Collectors.toList());

			user.setPhones(phones);

		}

		User savedUser = userRepository.save(user);

		return UserResponseDTO.builder().id(savedUser.getId()).created(user.getCreated()).modified(user.getModified())
				.lastLogin(user.getLastLogin()).token(user.getToken()).isActive(user.isActive()).build();

	}

	private Phone mapPhone(PhoneDTO dto, User user) {
		return Phone.builder().number(dto.getNumber()).citycode(dto.getCityCode()).contrycode(dto.getCountryCode())
				.user(user).build();
	}

}
