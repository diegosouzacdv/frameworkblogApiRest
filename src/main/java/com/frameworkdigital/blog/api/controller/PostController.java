package com.frameworkdigital.blog.api.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.frameworkdigital.blog.api.dto.ComentarioDTO;
import com.frameworkdigital.blog.api.dto.ImagemPostDTO;
import com.frameworkdigital.blog.api.dto.PostDTO;
import com.frameworkdigital.blog.api.input.ComentarioPostInput;
import com.frameworkdigital.blog.api.input.PostInput;
import com.frameworkdigital.blog.core.mapper.MapperComentario;
import com.frameworkdigital.blog.core.mapper.MapperPost;
import com.frameworkdigital.blog.core.storage.FotoStorage;
import com.frameworkdigital.blog.core.storage.FotoStorageRunnable;
import com.frameworkdigital.blog.domain.exception.PostNaoEncontradoException;
import com.frameworkdigital.blog.domain.model.Comentario;
import com.frameworkdigital.blog.domain.model.ImagensPost;
import com.frameworkdigital.blog.domain.model.Link;
import com.frameworkdigital.blog.domain.model.Post;
import com.frameworkdigital.blog.domain.model.Usuario;
import com.frameworkdigital.blog.domain.repository.ComentarioRepository;
import com.frameworkdigital.blog.domain.service.PostService;
import com.frameworkdigital.blog.domain.service.UsuarioService;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/posts")
@CrossOrigin(maxAge = 1800, origins = {"http://localhost:4200"})
public class PostController {
	
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MapperPost mapperPost;
	
	@Autowired
	private MapperComentario mapperComentario;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	private static final int tipoPostagem = 1;
	
	@GetMapping("/{id}")
	public ResponseEntity<?>  buscar(@PathVariable Long id) {
		try {
			 Post post =  postService.buscarPost(id);
			 
			 postService.contabilizarVisualizacao(post);
			 
			 return ResponseEntity.ok(mapperPost.mapperPost(post));
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Cadastra uma curtida para o post selecionado")
	@PutMapping(path = "/curtir/{id}",  name ="curtir" )
		public ResponseEntity<?>  curtir(@PathVariable Long id,@AuthenticationPrincipal Object usuarioLogado) {
		try {
			Post post =  postService.buscarPost(id);
			Usuario usuario = usuarioService.recuperarLogado(usuarioLogado);
			
			postService.curtir(post,usuario);
			
			return ResponseEntity.ok(mapperPost.mapperPost(post));
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "Cadastra uma comentário para o post selecionado")
	@PutMapping(path = "/comentar",  name ="comentar" )
		public ResponseEntity<?>  comentar(@RequestBody @Valid ComentarioPostInput comentarioPostInput,@AuthenticationPrincipal Object usuarioLogado ) {
		try {
			Usuario usuario = usuarioService.recuperarLogado(usuarioLogado);
			
			ComentarioDTO comentarioDTO =  mapperComentario.mapperInput(comentarioPostInput);
			comentarioDTO.setUsuarioId(usuario.getId());
			postService.comentar(comentarioDTO);
			
			Post post =  postService.buscarPost(comentarioDTO.getPostId());
			return ResponseEntity.ok(mapperPost.mapperPost(post));
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "Exclui um comentário para o post selecionado")
	@DeleteMapping(path = "/excluir/comentario/{id}"   )
		public ResponseEntity<?>  excluircomentar(@PathVariable Long id,@AuthenticationPrincipal Object usuarioLogado) {
		
		try {
			
			Optional<Comentario> comentarioOpt =  comentarioRepository.findById(id);
			
			Usuario usuario = usuarioService.recuperarLogado(usuarioLogado);
			
			if(usuario.getId().equals(comentarioOpt.get().getUsuario().getId())) {
				postService.excluirComentario(comentarioOpt.get());
			}
			else {
				return new ResponseEntity<>("SEM PERMISSAO PARA EXCLUIR ESTE COMENTÁRIO", HttpStatus.NOT_FOUND);
			}
			
			Post post =  postService.buscarPost(comentarioOpt.get().getPost().getId());
			return ResponseEntity.ok(mapperPost.mapperPost(post));
			
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista de comentários do post")
	@GetMapping("/comentarios/{id}")
	public ResponseEntity<?>  listaComentarios(@PathVariable Long id) {
		try {
			 Post post =  postService.buscarPost(id);
			 
			 return ResponseEntity.ok(mapperComentario.mapperList(post.getComentarios()));
			 
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	
	@ApiOperation(value = "Retorna uma lista de posts conforme filtro")
	@GetMapping
	public ResponseEntity<Page<PostDTO>>  filtrar(
			@RequestParam(value = "search[value]", required=false) String parametro,
			Pageable pageable
			) {
		
		List<Post> posts =  postService.buscarPostsParametro(parametro,pageable);
		List<PostDTO> retorno = mapperPost.mapperPostList(posts);
		int pageSize = pageable.getPageSize();
		long pageOffset = pageable.getOffset();
		long total = pageOffset + retorno.size() + (retorno.size() == pageSize ? pageSize : 0);
		Page<PostDTO> page = new PageImpl<PostDTO>(retorno, pageable,total);
		
		return ResponseEntity.ok().body(page) ;
	}
	
	
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>  cadastroComFoto(@RequestBody @Valid PostInput postInput,@AuthenticationPrincipal Object usuarioLogado) throws IOException {

		PostDTO postDTO = mapperPost.mapperPostInput(postInput);
		postDTO.setId(0l);
		postDTO.setTipoPostagem(tipoPostagem);
		
		postDTO.setUsuarioId(usuarioService.recuperarLogado(usuarioLogado).getId());//usuario logado
		
		Post post = postService.cadastrar(mapperPost.mapperPostDto(postDTO));
		
		postInput.getImagens().stream().forEach(e -> 
				gravarImagem(new ImagensPost(0l,post,e,e)));
		
		postInput.getLinks().stream().forEach(e -> 
				gravarLinks(new Link(0l,e,post)));
		
		
		return  buscar(post.getId());
		
	}
	
	@DeleteMapping()
	public ResponseEntity<?>  excluir(Long id,@AuthenticationPrincipal Object usuarioLogado) throws IOException {
		
		
		Usuario usuario = usuarioService.recuperarLogado(usuarioLogado);
		
		
		try {
			Post post =  postService.buscarPost(id);
			
			if(post.getUsuario().getId().equals(usuario.getId())) {
				postService.deletePost(post);
			}
			else {
				return new ResponseEntity<>("SEM PERMISSAO PARA EXCLUIR ESTE POST", HttpStatus.NOT_FOUND);
			}

			return ResponseEntity.ok("Excluido com sucesso");
		} catch (PostNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>("Erro ao excluir post ", HttpStatus.NOT_FOUND);
		}
		
		
	}

	
	@CrossOrigin(maxAge = 1800, origins = {"http://localhost:4200"})
	@PutMapping(path = "/imagens",  name ="imagens", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ImagemPostDTO uploadFotoUnica( MultipartFile foto) {
		DeferredResult<ImagemPostDTO> resultado = new DeferredResult<>();
		String caminho = "ImgensPosts"+File.separator;
		Thread thread = new Thread(new FotoStorageRunnable(foto, resultado, fotoStorage,caminho));
		thread.start();
		ImagemPostDTO retorno =null;
		while(retorno==null) {
			try { Thread.sleep (500); } catch (InterruptedException ex) {}
			retorno = (ImagemPostDTO) resultado.getResult();
		}
		
		return retorno;
	}
	
	
	private void gravarLinks(Link link) {
		postService.addLink(link);
	}
	
	
	private void gravarImagem(ImagensPost imagensPost) {
		postService.addImagem(imagensPost);
		
	}
	

}
