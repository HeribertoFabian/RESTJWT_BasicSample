package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.JWTAuthenticationToken;
import com.example.demo.model.JWTUser;
import com.example.demo.model.JWTUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	private JWTValidator validator;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();
		
		JWTUser jwtUser = validator.validate(token);
		
		if(jwtUser == null) {
			throw new RuntimeException("Jwt es incorrecto");
		}
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
		return new JWTUserDetails(jwtUser.getUserName(), token, jwtUser.getId(), grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return (JWTAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
