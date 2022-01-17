package com.example.demo.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.demo.security.JWTAuthenticationTokenFilter;
import com.example.demo.security.JWTAutheticationEntryPoint;
import com.example.demo.security.JwtAuthenticationProvider;
import com.example.demo.security.JwtSuccessHandler;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationProvider  authenticatorProveider;
	
	@Autowired
	private JWTAutheticationEntryPoint entryPoint;
	
	@Bean
	public AuthenticationManager authenticatorManager() {
		return new ProviderManager(Collections.singletonList(authenticatorProveider));
	}
	
	@Bean
	public JWTAuthenticationTokenFilter authenticationTokenFilter() {
		JWTAuthenticationTokenFilter filter = new JWTAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticatorManager());
		filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
		
		return filter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
		.authorizeRequests().antMatchers("**/api/**").authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(entryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}
}
