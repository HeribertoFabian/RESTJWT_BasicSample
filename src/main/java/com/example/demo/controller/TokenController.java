package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JWTUser;
import com.example.demo.model.Login;
import com.example.demo.security.JwtGenerator;

@RestController
@RequestMapping("/token")
public class TokenController {

	private JwtGenerator jwtGenerator;
	
	public TokenController(JwtGenerator jwtGenerator) {
		this.jwtGenerator = jwtGenerator;
	}
	
	@PostMapping
	public ResponseEntity<?> generate(@RequestBody final Login login){
		JWTUser jwtUser = new JWTUser();
		jwtUser = existUser(login);
		if(jwtUser != null) {
			List<String> lista = new ArrayList<>();
			lista.add(jwtGenerator.generate(jwtUser));
			return new ResponseEntity<List<String>>(lista, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	private JWTUser existUser(Login login) {
		JWTUser jwtUser;
		if(login.getUser().equals("alberto") && login.getPassword().equals("1234")) {
			jwtUser = new JWTUser();
			jwtUser.setUserName(login.getUser());
			jwtUser.setId(1);
			jwtUser.setRole("Admin");
			return jwtUser;			
		}else {
			return null;
		}
	}
	
}
