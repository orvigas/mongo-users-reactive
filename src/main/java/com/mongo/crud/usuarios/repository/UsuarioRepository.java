package com.mongo.crud.usuarios.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.crud.usuarios.model.Usuario;

import reactor.core.publisher.Flux;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

	@Query("{ id: { $exists: true }}")
	Flux<Usuario> retrieveAllQuotesPaged(final Pageable pagination);

}
