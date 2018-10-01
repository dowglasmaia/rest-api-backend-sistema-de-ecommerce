package com.maia.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.services.email.EmailService;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	// Class do Java que Gera Valores Aleatorios
	private Random rand = new Random();

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	// recuperando a Senha do usuario
	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		// Verificando se o email estar cadastrado da base de Dados!!!
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}

		String newPass = newPassword(); // Gera uma nova Senha
		cliente.setSenha(pe.encode(newPass)); // salva a nova Senha criptografada na Vase de Dados

		clienteRepository.save(cliente);

		emailService.sendNewPasswordEmail(cliente, newPass); // enviando a nova para o email correspondente ao cliente

	}

	// Gera uma Senha Aleatoria
	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// auxcilar na geração da senha con caracteres entre numeros, letras maiusculas
	// ou MAIUSCULAS.
	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {// gera um digito - numeros de 0 a 9 https://unicode-table.com/pt/
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minusculas
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
