package com.mongo.crud.usuarios.utility;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongo.crud.usuarios.repository.UsuarioRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
public class DbInitializer implements CommandLineRunner {

	private final UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) {
		usuarioRepository.count().map(number -> {
//			if (number == 0) {
//				log.info("Creating one user...");
//				return usuarioRepository.save(Usuario.builder().firstName("Lalo").lastName("LaloLanda")
//						.userName("lalo@something.com").password("SomePassword123").build());
//			}
			return number;
		}).subscribe(result -> {
			log.info("Result: {}", result.toString());
		});
	}

}
