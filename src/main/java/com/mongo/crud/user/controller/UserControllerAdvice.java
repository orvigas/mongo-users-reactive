package com.mongo.crud.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;

import com.mongo.crud.user.exception.ExceptionResponse;
import com.mongo.crud.user.exception.NotFoundException;

@ControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ExceptionResponse> notFoundException(final NotFoundException xcp) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodNotAllowedException.class)
	public final ResponseEntity<ExceptionResponse> methodNotAllowedException(
			final MethodNotAllowedException xcp) {
		return ResponseEntity.badRequest().body(ExceptionResponse.builder().code(HttpStatus.METHOD_NOT_ALLOWED.value())
				.status(HttpStatus.METHOD_NOT_ALLOWED).msg(xcp.getLocalizedMessage()).build());
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public final ResponseEntity<ExceptionResponse> webExchangeBindException(final WebExchangeBindException xcp) {
		return ResponseEntity.badRequest().body(ExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST).msg(xcp.getLocalizedMessage()).build());
	}

	@ExceptionHandler(ServerWebInputException.class)
	public final ResponseEntity<ExceptionResponse> serverWebInputException(final ServerWebInputException xcp) {
		return ResponseEntity.badRequest().body(ExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST).msg(xcp.getLocalizedMessage()).build());
	}

}
