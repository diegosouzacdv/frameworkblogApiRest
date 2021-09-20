package com.frameworkdigital.blog.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frameworkdigital.blog.api.dto.ComentarioDTO;
import com.frameworkdigital.blog.api.dto.PostDTO;
import com.frameworkdigital.blog.api.input.ComentarioPostInput;
import com.frameworkdigital.blog.api.input.PostInput;
import com.frameworkdigital.blog.domain.model.Comentario;

@Component
public class MapperComentario {

	@Autowired
	private ModelMapper modelMapper;
	
	public ComentarioDTO mapperEntityToDto(Comentario comentario) {
		ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
		return comentarioDTO;
	}
	
	public List<ComentarioDTO> mapperList(List<Comentario> comentarios) {
		List<ComentarioDTO> retorno = comentarios
				  .stream()
				  .map(com -> mapperEntityToDto(com))
				  .collect(Collectors.toList());
		return retorno;
	}
	
	public Comentario mapperDtoToEntity(ComentarioDTO comentarioDTO) {
		return modelMapper.map(comentarioDTO, Comentario.class);
	}
	
	public void copyDtoToEntity(ComentarioDTO comentarioDTO, Comentario comentario) {
		modelMapper.map(comentarioDTO, comentario);
	}
	
	public ComentarioDTO mapperInput(ComentarioPostInput input) {
		return modelMapper.map(input, ComentarioDTO.class);
	}
}
