package com.frameworkdigital.blog.api.dto;

public class ArquivoDTO {

	private String nome;
	private String contentType;
	private String diretorio;
	private String id;
	private String tipo;
	
	public ArquivoDTO(String nome, String contentType,String diretorio) {
		this.nome = nome;
		this.contentType = contentType;
		this.diretorio = diretorio;
		
	}
	
	public ArquivoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

	
}
