package com.maia.cursomc.services.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.maia.cursomc.domain.Cliente;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Testando envio de Email");
		LOG.info(msg.toString());
		LOG.info("Email Enviado com Sucesso!");

	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		LOG.info("Simulando o Envio de Email");
		LOG.info(cliente.getEmail() + " - Nova Senha: " + newPass);
		LOG.info("Email Enviado com Sucesso!");
		
	}

}
