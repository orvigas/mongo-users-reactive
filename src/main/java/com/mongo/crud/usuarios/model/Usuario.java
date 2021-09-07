package com.mongo.crud.usuarios.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "users")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 6612846463297860255L;

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;

}
