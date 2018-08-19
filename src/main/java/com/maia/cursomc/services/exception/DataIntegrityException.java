package com.maia.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}

	// Sobrecargo do Contrutor passando a causa de exception
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
