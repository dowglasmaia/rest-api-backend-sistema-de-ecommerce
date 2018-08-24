package com.maia.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.maia.cursomc.domain.enums.TipoPessoa;
import com.maia.cursomc.dto.ClienteNewDTO;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.resources.exception.FieldMessage;
import com.maia.cursomc.services.validation.utils.BR;

public class ClienteInsertValidation implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// validando CPF
		if (objDto.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpf())) {
			list.add(new FieldMessage("cpfOrCnpf", "CPF Inválido!"));
		}

		// validando CNPJ
		if (objDto.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA.getCod())
				&& !BR.isValidCNPJ(objDto.getCpfOrCnpf())) {
			list.add(new FieldMessage("cpfOrCnpf", "CNPJ Inválido!"));
		}

		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}