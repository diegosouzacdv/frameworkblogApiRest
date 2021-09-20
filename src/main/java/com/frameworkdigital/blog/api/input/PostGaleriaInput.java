package com.frameworkdigital.blog.api.input;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostGaleriaInput {
	
	private String titulo;
	
	private String descricao;
	
	private List<String> imagens;
	

}
