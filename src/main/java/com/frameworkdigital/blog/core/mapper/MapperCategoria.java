package com.frameworkdigital.blog.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frameworkdigital.blog.api.dto.CategoriaDTO;
import com.frameworkdigital.blog.domain.model.Categoria;

@Component
public class MapperCategoria {

	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaDTO mapperEntityToDto(Categoria categoria) {
		CategoriaDTO categoriaDTO = modelMapper.map(categoria, CategoriaDTO.class);
		return categoriaDTO;
	}
	
	public List<CategoriaDTO> mapperList(List<Categoria> categorias) {
		List<CategoriaDTO> retorno = categorias
				  .stream()
				  .map(cat -> mapperEntityToDto(cat))
				  .collect(Collectors.toList());
		return retorno;
	}
	
	public Categoria mapperDtoToEntity(CategoriaDTO categoriaDTO) {
		return modelMapper.map(categoriaDTO, Categoria.class);
	}
	
	public void copyDtoToEntity(CategoriaDTO categoriaDTO, Categoria categoria) {
		modelMapper.map(categoriaDTO, categoria);
	}
}
