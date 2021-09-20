package com.frameworkdigital.blog.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.frameworkdigital.blog.domain.model.Usuario;
import com.frameworkdigital.blog.domain.repository.UsuarioRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//  
	@Autowired
	private UsuarioRepository usuarioRepository;

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(authentication.getName());
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new UsernamePasswordAuthenticationToken(usuario.getId(), getPermissoes(),getPermissoes());
	}
	
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


	private Collection<? extends GrantedAuthority> getPermissoes() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		
		return authorities;
	}


	
}
