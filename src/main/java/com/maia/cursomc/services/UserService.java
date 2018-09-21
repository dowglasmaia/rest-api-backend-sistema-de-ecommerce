package com.maia.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.maia.cursomc.domain.security.UserSS;

public class UserService {

	// Retorna o Usuario Logado
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) {
			return null; // se nao tiver Usuario logado retorna nulo.
		}
	}

}
