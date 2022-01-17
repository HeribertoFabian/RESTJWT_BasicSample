package com.example.demo.security;

import org.springframework.stereotype.Component;

import com.example.demo.constants.Constants;
import com.example.demo.model.JWTUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTValidator {

	public JWTUser validate(String token) {
		JWTUser jwtUser = null;
		try {
			Claims body = Jwts.parser()
					.setSigningKey(Constants.YOUR_SECRET)
					.parseClaimsJws(token)
					.getBody();
			
			jwtUser = new JWTUser();
			jwtUser.setUserName(body.getSubject());
			jwtUser.setId(Long.parseLong((String) body.get(Constants.USER_ID)));
			jwtUser.setRole((String) body.get(Constants.ROLE));
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return jwtUser;
	}
}
