package com.frameworkdigital.blog.core.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile files,String local);

	public byte[] recuperarFotoTemporaria(String nome);

	public void salvar(String foto,String diretorio);

	public byte[] recuperar(String foto,String diretorio);

	public int remover(String nome,String diretorio);



}
