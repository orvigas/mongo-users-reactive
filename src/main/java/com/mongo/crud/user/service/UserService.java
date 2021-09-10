package com.mongo.crud.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mongo.crud.user.dto.UserDto;
import com.mongo.crud.user.exception.NotFoundException;
import com.mongo.crud.user.model.User;
import com.mongo.crud.user.repository.UserRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Slf4j
@Service
public class UserService {

	private final UserRepository userRepository;

	protected static final String NOT_FOUND_MESSAGE = "No records found for id: ";

	public Flux<UserDto> list(final Pageable pagination) {
		return userRepository.retrieveAllQuotesPaged(pagination).map(UserDto::from);
	}

	public Mono<UserDto> get(final String id) {
		return userRepository.findById(id).map(UserDto::from)
				.switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND_MESSAGE + id)));
	}

	public Mono<UserDto> store(final UserDto usuario) {
		log.info("Saving the user: {}", usuario);
		return userRepository.save(User.from(usuario)).map(UserDto::from);
	}

	public Mono<UserDto> update(final String id, final UserDto usuario) {
		log.info("updating the user identified with id: {}", id);
		return userRepository.findById(id).flatMap(dbInstance -> {
			BeanUtils.copyProperties(usuario, dbInstance, "id");
			return userRepository.save(dbInstance);
		}).map(UserDto::from).switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND_MESSAGE + id)));
	}

	public Mono<Void> delete(final String id) {
		log.info("Deleting the user identified with id: {}", id);
		return userRepository.deleteById(id);
	}

}
