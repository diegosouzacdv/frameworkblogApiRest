package com.frameworkdigital.blog.api.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frameworkdigital.blog.core.storage.FotoStorage;
import com.frameworkdigital.blog.domain.model.ImagensPost;
import com.frameworkdigital.blog.domain.service.ImagensPostService;



@RestController
@RequestMapping()
public class ImagenController {
	
	
	@Autowired
	private FotoStorage arquivoStorage;
	
	@Autowired
	private ImagensPostService imagensPostService;
	
	
	@GetMapping("/imagenspost/{id}")
	public ResponseEntity<byte[]>  buscar(@PathVariable Long id) {
		String nome = "";
		
		
		byte[] doc = null;
		ImagensPost foto = imagensPostService.buscarOuFalhar(id);
		String diretorioRaiz = "";
		if(foto!=null){
			 diretorioRaiz = "ImgensPosts"+File.separator;
				
				if(imagensPostService.existe(diretorioRaiz+File.separator+foto.getImagemNome())){
					nome = foto.getTipoReduzido();
					doc = arquivoStorage.recuperar(foto.getImagemNome(),diretorioRaiz);
				}
				else{
					nome = "jpg";
					doc = arquivoStorage.recuperar("semfoto.jpg","ImgensPosts");
				}
		
		}
		
		
		ResponseEntity<byte[]> retorno = imagensPostService.converteTipoRetorno(nome, doc);
		return retorno!=null?retorno:ResponseEntity.notFound().build();
	}
	
	
}
