package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.constants.Constants;
import com.example.demo.model.JWTAuthenticationToken;

public class JWTAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	public JWTAuthenticationTokenFilter() {
		super("/api/**");

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
		if (header == null || !header.startsWith(Constants.BEARER_TOKEN)) {
			throw new RuntimeException("Jwt  es incorrecto o no ha llegado nada");
		}
		
		String authenticationToken = header.substring(7);
		JWTAuthenticationToken token = new JWTAuthenticationToken(authenticationToken);

		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}
