package com.frameworkdigital.blog.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.frameworkdigital.blog.api.dto.ComentarioDTO;
import com.frameworkdigital.blog.core.mapper.MapperComentario;
import com.frameworkdigital.blog.domain.exception.PostNaoEncontradoException;
import com.frameworkdigital.blog.domain.filter.PostFilter;
import com.frameworkdigital.blog.domain.model.Comentario;
import com.frameworkdigital.blog.domain.model.Curtidas;
import com.frameworkdigital.blog.domain.model.ImagensPost;
import com.frameworkdigital.blog.domain.model.Link;
import com.frameworkdigital.blog.domain.model.Post;
import com.frameworkdigital.blog.domain.model.Usuario;
import com.frameworkdigital.blog.domain.repository.ComentarioRepository;
import com.frameworkdigital.blog.domain.repository.CurtidasRepository;
import com.frameworkdigital.blog.domain.repository.ImagensPostRepository;
import com.frameworkdigital.blog.domain.repository.LinkRepository;
import com.frameworkdigital.blog.domain.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private LinkRepository linkRepository;
	
	@Autowired
	private ImagensPostRepository imagensPostRepository;
	@Autowired
	private CurtidasRepository curtidasRepository;
	
	@Autowired
	private MapperComentario mapperComentario;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public Post buscarPost(Long id) {
		return buscarOuFalhar(id);
	}
	
	
	public List<Post> buscarPosts() {
		return postRepository.findAll();
	}

	public Post cadastrar(Post post) {
		
		
		if(post.getDataHoraPublicacao()==null) {
			post.setDataHoraPublicacao(LocalDateTime.now());
		}
		
		return postRepository.save(post);
	}

	public Post buscarOuFalhar(Long postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new PostNaoEncontradoException(postId));
	}
	
	
	public Post addLink(Link link) {
		
		link = linkRepository.save(link);
		
		return link.getPost();
	}
	
	
	public Post addImagem(ImagensPost imagensPost) {
		imagensPost = imagensPostRepository.save(imagensPost);
		return imagensPost.getPost();
	}


	public List<Post> buscarPostsParametro(String parametro,Pageable pageable) {
		
		PostFilter postFilter = new PostFilter();
		postFilter.setParametro(parametro);
		return postRepository.findByFilter(postFilter,pageable);
	}


	public void deletePost(Post post) {
		postRepository.delete(post);
	}


	public void curtir(Post post, Usuario usuario) {
		Curtidas  curtidas = new Curtidas(0l,LocalDateTime.now(),usuario,post);
		
		List<Curtidas> curtidasList = curtidasRepository.findByPostIdAndUsuarioId(post.getId(),usuario.getId());
		
		if(curtidasList.size()==0) {
			curtidasRepository.save(curtidas);
		}
	
	}
	
	public void comentar(ComentarioDTO comentarioDto) {
			Comentario comentario = mapperComentario.mapperDtoToEntity(comentarioDto);
			comentario.setDataHoraPublicacao(LocalDateTime.now());
			comentarioRepository.save(comentario);
	}


	public void excluirComentario(Comentario comentario) {
		
		comentarioRepository.delete(comentario);
		
	}


	public Post contabilizarVisualizacao(Post post) {
		post.setVisualizacoes(post.getVisualizacoes()+1);
		return postRepository.save(post);
		
	}
	

}
