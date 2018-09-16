package com.maia.cursomc.domain.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}") // pega a chave do application.properties
	private String secret;

	@Value("${jwt.expiration}") // pega a chave do application.properties
	private Long expiration;

	//Metodo que Gera o Token 
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username) // pega o usuario passado no paramento
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // define o tempo de expiração
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // define o algoritimo de autenticação
				.compact();
	}

}
