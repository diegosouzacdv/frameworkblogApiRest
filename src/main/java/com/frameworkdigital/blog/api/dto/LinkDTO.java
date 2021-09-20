package com.frameworkdigital.blog.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkDTO {
	
	private Long id;
	private Long postId;
	private String descricao;
	
}
