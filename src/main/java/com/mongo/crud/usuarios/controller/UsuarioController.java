package com.mongo.crud.usuarios.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.crud.usuarios.model.Usuario;
import com.mongo.crud.usuarios.service.UsuarioService;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Data
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;

	@GetMapping
	public Flux<Usuario> index(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) List<String> sortBy,
			@RequestParam(defaultValue = "ASC", required = false) String sort) {
		final Pageable pagination = PageRequest.of(page, size,
				Sort.by(Direction.valueOf(sort.toUpperCase()), sortBy.toArray(new String[sortBy.size()])));
		return usuarioService.list(pagination);
	}

	@GetMapping("/{id}")
	public Mono<Usuario> get(@PathVariable(name = "id", required = true) final String id) {
		return usuarioService.get(id);
	}

	@PostMapping
	public Mono<Usuario> create(@RequestBody @NonNull final Usuario usuario) {
		return usuarioService.store(usuario);
	}

	@PutMapping("/{id}")
	public Mono<Usuario> update(@PathVariable(name = "id", required = true) final String id,
			@RequestBody @NonNull final Usuario usuario) {
		return usuarioService.update(id, usuario);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> destroy(@PathVariable(name = "id", required = true) final String id) {
		return usuarioService.delete(id);
	}

}
