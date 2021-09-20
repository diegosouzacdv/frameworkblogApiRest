package com.frameworkdigital.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frameworkdigital.blog.api.dto.CategoriaDTO;
import com.frameworkdigital.blog.core.mapper.MapperCategoria;
import com.frameworkdigital.blog.domain.exception.CategoriaNaoEncontradoException;
import com.frameworkdigital.blog.domain.model.Categoria;
import com.frameworkdigital.blog.domain.service.CategoriaService;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private MapperCategoria mapperUsuario;
	
	@GetMapping("/{id}")
	public ResponseEntity<?>  buscar(@PathVariable Long id) {
		try {
			Categoria categoria =  categoriaService.buscarCategoria(id);
			 return ResponseEntity.ok(mapperUsuario.mapperEntityToDto(categoria));
		} catch (CategoriaNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping
	public List<CategoriaDTO> listar() {
		List<Categoria> users =  categoriaService.buscarCategorias();
		return  mapperUsuario.mapperList(users);
		
	}
	
	

}
