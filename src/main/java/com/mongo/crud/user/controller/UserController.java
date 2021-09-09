package com.mongo.crud.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.crud.user.dto.UserDto;
import com.mongo.crud.user.service.UserService;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Data
@Validated
@RestController
@RequestMapping("/usuario")
public class UserController {

	private final UserService userService;

	@GetMapping
	public Flux<UserDto> index(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) List<String> sortBy,
			@RequestParam(defaultValue = "ASC", required = false) String sort) {
		final Pageable pagination = PageRequest.of(page, size,
				Sort.by(Direction.valueOf(sort.toUpperCase()), sortBy.toArray(new String[sortBy.size()])));
		return userService.list(pagination);
	}

	@GetMapping("/{id}")
	public Mono<UserDto> get(@PathVariable(name = "id", required = true) final String id) {
		return userService.get(id);
	}

	@PostMapping
	public Mono<UserDto> create(@RequestBody @NonNull @Valid UserDto usuario) {
		return userService.store(usuario);
	}

	@PutMapping("/{id}")
	public Mono<UserDto> update(@PathVariable(name = "id", required = true) final String id,
			@RequestBody @NonNull @Valid final UserDto usuario) {
		return userService.update(id, usuario);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> destroy(@PathVariable(name = "id", required = true) final String id) {
		return userService.delete(id);
	}

}
