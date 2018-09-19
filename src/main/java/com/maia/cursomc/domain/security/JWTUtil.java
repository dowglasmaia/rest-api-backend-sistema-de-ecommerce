package com.maia.cursomc.domain.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}") // pega a chave do application.properties
	private String secret;

	@Value("${jwt.expiration}") // pega a chave do application.properties
	private Long expiration;

	// Metodo que Gera o Token
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username) // pega o usuario passado no paramento
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // define o tempo de expiração
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // define o algoritimo de autenticação
				.compact();
	}

	// Verifica se o Tem e Valido
	public boolean tokenValido(String token) {
		// Armazena as Reivindicações do token
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject(); // pega o usuario
			Date expirationDate = claims.getExpiration(); // pega a Data de Expiração
			Date now = new Date(System.currentTimeMillis()); // pega a Data Atual do Sistema
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;

	}

	//Pega o usuario Apartir do Token
	public String getUsername(String token) {
		// Armazena as Reivindicações do token
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	// Recupera os Claims apartir do Token , se for invalido ou nulo o retorno e
	// NULL
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
