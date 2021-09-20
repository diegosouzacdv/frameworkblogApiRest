package com.frameworkdigital.blog.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTO {
	
	private Long id;

	private String nome
	;
	private String action = "categorias";
	
	public String getAction() {
		return "/"+action+"/"+id;
	}
	
}
