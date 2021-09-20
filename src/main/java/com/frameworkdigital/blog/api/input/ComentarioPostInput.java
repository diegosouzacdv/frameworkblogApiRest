package com.frameworkdigital.blog.api.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ComentarioPostInput {
	
	private String descricao;

	private Long postId;
	

}
