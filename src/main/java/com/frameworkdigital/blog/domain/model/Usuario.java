package com.frameworkdigital.blog.domain.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "USUARIO")
public class Usuario {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USU_CODIGO",unique=true, nullable=false)
	private Long id;
	
	@Column(name = "USU_DATACADASTRO")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	
	@Column(name="USU_NOME")
	private String nome;
	
	
	@Column(name="USU_EMAIL")
	private String email;
	
	
	@Column(name="USU_SENHA")
	private String senha;
	
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Post> posts;
	

	
	
	public boolean senhaCoincideCom(String senha) {
		return getSenha().equals(senha);
	}
	
	public boolean senhaNaoCoincideCom(String senha) {
		return !senhaCoincideCom(senha);
	}



}
