package com.maia.cursomc.services.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}") // Pega o Valor da chave do properties
	private String sender;

	//envia o email de confirmação de Pedido
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailFromPedido(obj);
		sendEmail(sm);
	}

	//Metodo auxilar para envia o email de Pedido
	protected SimpleMailMessage prepareSimpleMailFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	//envia o email com niva Senha
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass){
		SimpleMailMessage sm = prepareSimpleMailFromPedido( cliente, newPass);
		sendEmail(sm);
		
		
	}

	//Metodo auxilar para envia o email com nova Senha
	protected SimpleMailMessage prepareSimpleMailFromPedido(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de Nova Senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova Senha: "+ newPass);
		return sm;
	}

}
