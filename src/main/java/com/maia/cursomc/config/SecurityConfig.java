package com.maia.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.maia.cursomc.domain.security.JWTAuthenticationFilter;
import com.maia.cursomc.domain.security.JWTAuthorizationFilter;
import com.maia.cursomc.domain.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	// metodo que informa quas url estão liberas para acesso
	private static final String[] PUBLIC_MATCHERS = { 
			"/h2-console/**" };

	// Somente Leitura dos Dados
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**", "/categorias/**", "/clientes/**", "/estados/**" };
	
	// Somente Post 
	private static final String[] PUBLIC_MATCHERS_POST = { 
			"/clientes/",  /* Cliente deve Estar Logado para fazer requisições de Post*/
			"/auth/forgot/**"
			};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// liberando todos os acessos quando em fase de test. para usar o profeli de
		// test e acesso o h2-console
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.antMatchers(PUBLIC_MATCHERS)// passo meu vetor de url permitidas
				.permitAll() // permite todos do meu metodo criado acima
				.anyRequest().authenticated(); // para todo o resto exige autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); //Autenticação do Token do JWT
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService)); // Autorização do token
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // ACEGURA QUE O Back-End Não
																							// Cria sessão de Usuairo
	}

	// atenticando o usario pegando o Email e a Senha autenticada!
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;

	}

	// Metodo para fazer a Cryptografia da senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
