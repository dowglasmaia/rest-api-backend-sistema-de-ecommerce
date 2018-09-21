package com.maia.cursomc.services.exception;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg);
	}

	// Sobrecargo do Contrutor passando a causa de exception
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
