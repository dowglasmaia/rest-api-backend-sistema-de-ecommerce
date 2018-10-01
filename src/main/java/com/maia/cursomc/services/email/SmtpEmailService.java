package com.maia.cursomc.services.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.maia.cursomc.domain.Cliente;

/*
 * Class Que Envia o Email*/

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);
		LOG.info("Email Enviado com Sucesso!");

	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		// TODO Auto-generated method stub
		
	}

}
