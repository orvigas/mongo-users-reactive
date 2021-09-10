package com.mongo.crud.user.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;

import com.mongo.crud.user.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class UserControllerAdviceTest {

	UserControllerAdvice userControllerAdvice;

	static final String ERROR_MESSAGE = "Any error message";
	WebExchangeBindException webExchangeBindException;

	@BeforeEach
	void setUp() {
		userControllerAdvice = new UserControllerAdvice();
		webExchangeBindException = mock(WebExchangeBindException.class);
	}

	@Test
	void testNotFoundException() {
		var instance = userControllerAdvice.notFoundException(new NotFoundException(ERROR_MESSAGE));
		assertNotNull(instance);

	}

	@Test
	void testWebExchangeBindException() {
		var instance = userControllerAdvice.webExchangeBindException(webExchangeBindException);
		assertNotNull(instance);
	}

	@Test
	void testServerWebInputException() {
		var instance = userControllerAdvice.serverWebInputException(new ServerWebInputException(ERROR_MESSAGE));
		assertNotNull(instance);
	}

	@Test
	void testMethodNotAllowedException() {
		var instance = userControllerAdvice.methodNotAllowedException(new MethodNotAllowedException("POST", null));
		assertNotNull(instance);
	}
}
