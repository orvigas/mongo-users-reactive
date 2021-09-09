package com.mongo.crud.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mongo.crud.user.dto.UserDto;
import com.mongo.crud.user.exception.NotFoundException;
import com.mongo.crud.user.model.User;
import com.mongo.crud.user.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	User user;
	UserDto userDto;

	protected static final String NOT_FOUND_MESSAGE = "No records found for id: ";

	@BeforeEach
	void setUp() {
		user = new User();
		user.setId("12345qwerty");
		userDto = new UserDto();
		userDto.setId(user.getId());
	}

	@Test
	void testList() {
		when(userRepository.retrieveAllQuotesPaged(any(Pageable.class))).thenReturn(Flux.just(user));
		StepVerifier.create(userService.list(PageRequest.of(0, 1)))
				.assertNext(result -> result.getId().equalsIgnoreCase(user.getId())).verifyComplete();
	}

	@Test
	void testGet() {
		when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
		StepVerifier.create(userService.get(userDto.getId()))
				.assertNext(result -> result.getId().equalsIgnoreCase(user.getId())).verifyComplete();
	}

	@Test
	void testGetNotFoundException() {
		when(userRepository.findById(anyString()))
				.thenReturn(Mono.error(new NotFoundException(NOT_FOUND_MESSAGE + userDto.getId())));
		StepVerifier.create(userService.get(userDto.getId())).expectErrorMessage(NOT_FOUND_MESSAGE + userDto.getId())
				.verify();
	}

	@Test
	void testStore() {
		when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
		StepVerifier.create(userService.store(userDto))
				.assertNext(result -> result.getId().equalsIgnoreCase(user.getId())).verifyComplete();
	}

	@Test
	void testUpdate() {

		when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
		when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
		StepVerifier.create(userService.update(userDto.getId(), userDto))
				.assertNext(result -> result.getId().equalsIgnoreCase(user.getId())).verifyComplete();
	}

	@Test
	void testUpdateNotFoundException() {
		when(userRepository.findById(anyString()))
				.thenReturn(Mono.error(new NotFoundException(NOT_FOUND_MESSAGE + userDto.getId())));
		StepVerifier.create(userService.update(userDto.getId(), userDto))
				.expectErrorMessage(NOT_FOUND_MESSAGE + userDto.getId()).verify();
	}

	@Test
	void testDelete() {
		when(userRepository.findById(userDto.getId())).thenReturn(Mono.just(user));
		when(userRepository.deleteById(user.getId())).thenReturn(Mono.empty());
		StepVerifier.create(userService.delete(user.getId())).expectErrorMessage(NOT_FOUND_MESSAGE + userDto.getId())
				.verify();
	}

}
