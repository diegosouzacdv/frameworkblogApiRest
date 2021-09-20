package com.frameworkdigital.blog.core.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class UsuarioSistema  extends UsernamePasswordAuthenticationToken{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public UsuarioSistema(Long id, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(id, password, authorities);
		
	}

	

}

