package com.frameworkdigital.blog.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImagemPostDTO {
	
	
	private Long id;
	private Long postId;
  	private String imagemNome;
  	private String imagemContentType;
  	
  	private String action="imagenspost";
  	
  	public String getAction() {
  		return "/"+action+"/"+id;
  	}

	public ImagemPostDTO(String imagemNome, String imagemContentType, String action) {
		super();
		this.imagemNome = imagemNome;
		this.imagemContentType = imagemContentType;
		this.action = action;
	}
  	
  	
	
}
