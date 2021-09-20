package com.frameworkdigital.blog.domain.exception;


public class CategoriaNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CategoriaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CategoriaNaoEncontradoException(Long categoriaId) {
		this(String.format("Não existe um cadastro de categoria com código %d", categoriaId));
	}
	
}
