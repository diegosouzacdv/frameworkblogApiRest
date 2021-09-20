package com.frameworkdigital.blog.api.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComentarioDTO {
	
	private Long id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy hh:MM:ss")
	private LocalDateTime dataHoraPublicacao;
	
	private Long usuarioId;

	private Long postId;
	
	private String descricao;
	
	private Long fotoId;
	
	private String action = "comentarios";
	
	public String getAction() {
		return "/"+action+"/"+id;
	}

}
