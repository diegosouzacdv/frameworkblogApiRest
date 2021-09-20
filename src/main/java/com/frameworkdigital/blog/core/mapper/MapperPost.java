package com.frameworkdigital.blog.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frameworkdigital.blog.api.dto.PostDTO;
import com.frameworkdigital.blog.api.input.PostGaleriaInput;
import com.frameworkdigital.blog.api.input.PostInput;
import com.frameworkdigital.blog.domain.model.Post;

@Component
public class MapperPost {

	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public PostDTO mapperPost(Post post) {
		PostDTO postDTO = modelMapper.map(post, PostDTO.class);
		return postDTO;
	}
	
	public List<PostDTO> mapperPostList(List<Post> posts) {
		List<PostDTO> retorno = posts
				  .stream()
				  .map(post -> mapperPost(post))
				  .collect(Collectors.toList());
		return retorno;
	}

	public Post mapperPostDto(PostDTO postDTO) {
		return modelMapper.map(postDTO, Post.class);
	}
	
	public PostDTO mapperPostInput(PostInput input) {
		return modelMapper.map(input, PostDTO.class);
	}

	public PostDTO mapperGleriaPostInput(PostGaleriaInput postInput) {
		return modelMapper.map(postInput, PostDTO.class);
	}
	
	
}
