package com.frameworkdigital.blog.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDTO {
	
	private String exp;
	private String user_name;
	private List<String> authorities;
	private String jti;
	private String client_id;
	private List<String> scope;
	
	public JwtTokenDTO(String exp, String user_name, List<String> authorities, String jti, String client_id,
			List<String> scope) {
		super();
		this.exp = exp;
		this.user_name = user_name;
		this.authorities = authorities;
		this.jti = jti;
		this.client_id = client_id;
		this.scope = scope;
	}
	
	
	
	
}


