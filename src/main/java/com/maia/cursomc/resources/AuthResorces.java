package com.maia.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maia.cursomc.domain.security.JWTUtil;
import com.maia.cursomc.domain.security.UserSS;
import com.maia.cursomc.dto.EmailDTO;
import com.maia.cursomc.services.AuthService;
import com.maia.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResorces {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;

	//endpoint que Atualiza o Token Automaticamente! do usuario logado
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization"); //libera a Leitura do Authorization por CORS
		return ResponseEntity.noContent().build();
	}
	
	//endpoint Enviar email com nova Senha
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		service.sendNewPassword(emailDTO.getEmail());		
		return ResponseEntity.noContent().build();
	}

}
