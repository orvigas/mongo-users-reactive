package com.mongo.crud.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.crud.user.model.User;

import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

	@Query("{ id: { $exists: true }}")
	Flux<User> retrieveAllQuotesPaged(final Pageable pagination);

}
