package com.frameworkdigital.blog.domain.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.frameworkdigital.blog.domain.exception.ImagensPostNaoEncontradoException;
import com.frameworkdigital.blog.domain.model.ImagensPost;
import com.frameworkdigital.blog.domain.repository.ImagensPostRepository;

@Service
public class ImagensPostService {
	
	
	@Autowired
	private ImagensPostRepository imagensPostRepository;
	
	
	
	public ImagensPost buscarOuFalhar(Long id) {
		return imagensPostRepository.findById(id)
				.orElseThrow(() -> new ImagensPostNaoEncontradoException(id));
	}
	
	
	public boolean existe(String nome) {
		File arquivo = new File(defaultDirectory()+File.separator+nome);
		//verificar existencia do arquiv
		if(arquivo.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static String defaultDirectory()
	{
	    String OS = System.getProperty("os.name").toUpperCase();
	    
	    String nome = "";
	    
	    if (OS.contains("WIN"))
	    	nome = System.getenv("APPDATA");
	    else if (OS.contains("MAC"))
	    	nome = System.getProperty("user.home") + "/Library/Application "
	                + "Support";
	    else if (OS.contains("NUX"))
	    	nome = File.separator+"usr"+File.separator+"share"+File.separator+"tomcat8";
	   
	    return nome;
	}
	
	
	
	/**
	 * VERIFICA O TIPO DE RETORNO NA VISUALIZAÇÃO DO ANEXO
	 * @param anexo
	 * @param doc
	 * @return
	 */
	public ResponseEntity<byte[]> converteTipoRetorno(String anexo, byte[] doc) {
		return verificaTipo(anexo, doc);
	}
	
	
	public ResponseEntity<byte[]> verificaTipo(String tipo, byte[] doc) {
		if(tipo.toLowerCase().equals("pdf")){
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE) 
					.body(doc);
		}
		else{
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE) 
					.body(doc);
		}
	}

}
