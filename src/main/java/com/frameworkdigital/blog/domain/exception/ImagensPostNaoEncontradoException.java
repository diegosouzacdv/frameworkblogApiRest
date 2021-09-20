package com.frameworkdigital.blog.domain.exception;


public class ImagensPostNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ImagensPostNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ImagensPostNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de imagem com código %d", id));
	}
	
}
