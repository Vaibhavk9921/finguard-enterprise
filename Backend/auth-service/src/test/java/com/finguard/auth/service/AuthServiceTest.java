package com.finguard.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finguard.auth.dto.ApiResponse;
import com.finguard.auth.dto.LoginRequest;
import com.finguard.auth.dto.RegisterRequest;
import com.finguard.auth.dto.UserRegisteredEvent;
import com.finguard.auth.entity.Role;
import com.finguard.auth.entity.User;
import com.finguard.auth.exception.InvalidCredentialsException;
import com.finguard.auth.kafka.UserEventProducer;
import com.finguard.auth.repository.UserRepository;
import com.finguard.auth.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private UserEventProducer userEventProducer;

	@InjectMocks
	private AuthService authService;

	@Test
	void shouldRegisterUserSuccessfully() {

		RegisterRequest request = new RegisterRequest();
		request.setName("Vaibhav");
		request.setEmail("test@gmail.com");
		request.setPassword("password123");

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

		User savedUser = new User();
		savedUser.setId(1L);
		savedUser.setName("Vaibhav");
		savedUser.setEmail(request.getEmail());
		savedUser.setPassword("encodedPassword");
		savedUser.setRole(Role.USER);

		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		ApiResponse<Void> response = authService.register(request);

		assertTrue(response.isSucess());
		assertEquals("User Registered Successfully", response.getMessage());

		verify(userRepository).save(any(User.class));
		verify(userEventProducer).publishUserRegisteredEvent(any(UserRegisteredEvent.class));
	}

	@Test
	void shouldThrowExceptionWhenEmailAlreadyExists() {

		RegisterRequest request = new RegisterRequest();
		request.setEmail("test@gmail.com");

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.register(request));

		assertEquals("Email Already Exists", ex.getMessage());

		verify(userRepository, never()).save(any());
	}

	@Test
	void shouldLoginSuccessfully() {

		LoginRequest request = new LoginRequest();
		request.setEmail("test@gmail.com");
		request.setPassword("password");

		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword("encoded");
		user.setRole(Role.USER);

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

		when(passwordEncoder.matches("password", "encoded")).thenReturn(true);

		when(jwtUtil.generateToken(user.getEmail(), "USER")).thenReturn("jwt-token");

		String token = authService.login(request);

		assertEquals("jwt-token", token);
	}

	@Test
	void shouldThrowExceptionForInvalidPassword() {

		LoginRequest request = new LoginRequest();
		request.setEmail("test@gmail.com");
		request.setPassword("wrong");

		User user = new User();
		user.setPassword("encoded");

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

		when(passwordEncoder.matches(any(), any())).thenReturn(false);

		assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
	}

	@Test
	void shouldThrowExceptionWhenUserNotFound() {

		LoginRequest request = new LoginRequest();
		request.setEmail("abc@gmail.com");

		when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

		assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
	}

	@Test
	void shouldGetAllUsers() {

		User user = new User();
		user.setId(1L);
		user.setName("Vaibhav");
		user.setEmail("test@gmail.com");
		user.setRole(Role.USER);

		when(userRepository.findAll()).thenReturn(List.of(user));

		assertEquals(1, authService.getAllUsers().size());

		verify(userRepository).findAll();
	}
}
