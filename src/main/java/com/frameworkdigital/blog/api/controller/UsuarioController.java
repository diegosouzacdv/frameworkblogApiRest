package com.frameworkdigital.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frameworkdigital.blog.api.dto.SenhaDTO;
import com.frameworkdigital.blog.api.dto.UsuarioDTO;
import com.frameworkdigital.blog.core.mapper.MapperUsuario;
import com.frameworkdigital.blog.domain.exception.NegocioException;
import com.frameworkdigital.blog.domain.exception.UsuarioNaoEncontradoException;
import com.frameworkdigital.blog.domain.model.Usuario;
import com.frameworkdigital.blog.domain.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(maxAge = 1800, origins = {"http://localhost:4200"})
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MapperUsuario mapperUsuario;
	
	@GetMapping("/{id}")
	public ResponseEntity<?>  buscar(@PathVariable Long id) {
		try {
			Usuario usuario =  usuarioService.buscarUsuario(id);
			 return ResponseEntity.ok(mapperUsuario.mapperEntityToDto(usuario));
		} catch (UsuarioNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/usuario")
	public ResponseEntity<?>  buscarUsuario(@AuthenticationPrincipal Object usuarioLogado) {
		try {
			Usuario usuario = usuarioService.recuperarLogado(usuarioLogado);
			 return ResponseEntity.ok(mapperUsuario.mapperEntityToDto(usuario));
		} catch (UsuarioNaoEncontradoException msg) {
			ResponseEntity.notFound().build();
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping
	public List<UsuarioDTO> listar() {
		List<Usuario> users =  usuarioService.buscarUsuarios();
		return  mapperUsuario.mapperList(users);
		
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		
		try {
			Usuario usuario = mapperUsuario.mapperDtoToEntity(usuarioDTO);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuario.setSenha(encoder.encode(usuarioDTO.getSenha()));
			
			
			usuario = usuarioService.salvar(usuario);
			return ResponseEntity.ok(mapperUsuario.mapperEntityToDto(usuario));
		} catch (NegocioException msg) {
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
		}
		
		
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioDTO usuarioDTO) {
		
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		mapperUsuario.copyDtoToEntity(usuarioDTO, usuarioAtual);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioAtual.setSenha(encoder.encode(usuarioDTO.getSenha()));
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return mapperUsuario.mapperEntityToDto(usuarioAtual);
	}
	
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public  ResponseEntity<?> alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaDTO senha) {
		try {
			usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
			return ResponseEntity.ok("Senha alterada com sucesso :)");
		} catch (NegocioException msg) {
			return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
		}
	}

}
