package com.maia.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long time) {
		super(status, msg, time);

	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String msg) {
		erros.add(new FieldMessage(fieldName, msg));
	}

}
