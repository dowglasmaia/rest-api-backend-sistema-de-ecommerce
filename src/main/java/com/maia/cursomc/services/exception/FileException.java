package com.maia.cursomc.services.exception;

public class FileException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg);
	}

	// Sobrecargo do Contrutor passando a causa de exception
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
