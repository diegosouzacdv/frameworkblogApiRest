package com.frameworkdigital.blog.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
	
	private long id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy hh:MM:ss")
	private LocalDateTime dataHoraPublicacao;
	
	private String titulo;
	
	private String descricao;
	
	private int visualizacoes;
	
	private Long categoriaId;
	
	private Long usuarioId;
	
	private String usuarioNome;
	
	private List<LinkDTO> links;
	
	
	private List<ImagemPostDTO> imagens;
	
	private int totalCurtidas;
	
	private int totalComentarios;
	
	private int tipoPostagem;

	
	private String action = tipoPostagem==1?"posts":"galerias";
	
	public String getAction() {
		return "/"+action+"/"+id;
	}

	
	


}
