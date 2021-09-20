package com.frameworkdigital.blog.domain.exception;


public class PostNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PostNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PostNaoEncontradoException(Long postId) {
		this(String.format("Não existe um cadastro de post com código %d", postId));
	}
	
}
