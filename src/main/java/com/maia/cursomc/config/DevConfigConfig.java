package com.maia.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.maia.cursomc.services.DBService;
import com.maia.cursomc.services.email.EmailService;
import com.maia.cursomc.services.email.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfigConfig {

	@Autowired
	private DBService dbService;

	// virificando a chave de configuração do BD
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}

		dbService.instanciateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
