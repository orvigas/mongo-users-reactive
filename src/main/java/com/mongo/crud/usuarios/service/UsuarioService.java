package com.mongo.crud.usuarios.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.mongo.crud.usuarios.model.Usuario;
import com.mongo.crud.usuarios.repository.UsuarioRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Slf4j
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public Flux<Usuario> list(final org.springframework.data.domain.Pageable pagination) {
		return usuarioRepository.retrieveAllQuotesPaged(pagination);
	}

	public Mono<Usuario> get(final String id) {
		log.info("Looking for the user identified with id: {}", id);
		return usuarioRepository.findById(id);
	}

	public Mono<Usuario> store(final Usuario usuario) {
		log.info("Saving the user: {}", usuario);
		return usuarioRepository.save(usuario);
	}

	public Mono<Usuario> update(final String id, final Usuario usuario) {
		log.info("updating the user identified with id: {}", id);
		return usuarioRepository.existsById(id).flatMap(exist -> {
			if (exist) {
				return usuarioRepository.save(usuario);
			}
			return Mono.error(new NotFoundException());
		});

	}

	public Mono<Void> delete(final String id) {
		log.info("Deleting the user identified with id: {}", id);
		return usuarioRepository.existsById(id).flatMap(exist -> {
			if (exist) {
				return usuarioRepository.deleteById(id);
			}
			return Mono.error(new NotFoundException());
		});
	}

}
