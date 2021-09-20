package com.frameworkdigital.blog.api.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
	
	private Long id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	private String nome;
	private String email;
	private String senha;
	
	private String action = "usuarios";
	
	public String getAction() {
		return "/"+action+"/"+id;
	}
}
