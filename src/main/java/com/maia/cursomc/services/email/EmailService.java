package com.maia.cursomc.services.email;

import org.springframework.mail.SimpleMailMessage;

import com.maia.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
