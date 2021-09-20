package com.frameworkdigital.blog.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frameworkdigital.blog.domain.exception.CategoriaNaoEncontradoException;
import com.frameworkdigital.blog.domain.model.Categoria;
import com.frameworkdigital.blog.domain.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarCategoria(Long id) {
		return buscarOuFalhar(id);
	}
	
	public List<Categoria> buscarCategorias() {
		return categoriaRepository.findAll();
	}
	
	public Categoria buscarOuFalhar(Long categoriaId) {
		return categoriaRepository.findById(categoriaId).orElseThrow(() -> new CategoriaNaoEncontradoException(categoriaId));
	}
	

	
}
