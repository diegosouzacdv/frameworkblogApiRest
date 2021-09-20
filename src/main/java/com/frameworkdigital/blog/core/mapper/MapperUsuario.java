package com.frameworkdigital.blog.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frameworkdigital.blog.api.dto.UsuarioDTO;
import com.frameworkdigital.blog.domain.model.Usuario;

@Component
public class MapperUsuario {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO mapperEntityToDto(Usuario usuario) {
		UsuarioDTO postDTO = modelMapper.map(usuario, UsuarioDTO.class);
		return postDTO;
	}
	
	public List<UsuarioDTO> mapperList(List<Usuario> usuarios) {
		List<UsuarioDTO> retorno = usuarios
				  .stream()
				  .map(user -> mapperEntityToDto(user))
				  .collect(Collectors.toList());
		return retorno;
	}
	
	public Usuario mapperDtoToEntity(UsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO, Usuario.class);
	}
	
	public void copyDtoToEntity(UsuarioDTO usuarioDTO, Usuario usuario) {
		modelMapper.map(usuarioDTO, usuario);
	}
}
