package com.maia.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maia.cursomc.domain.security.JWTUtil;
import com.maia.cursomc.domain.security.UserSS;
import com.maia.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResorces {

	@Autowired
	private JWTUtil jwtUtil;

	//metodo que Atualiza o Token Automaticamente! do usuario logado
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

}
