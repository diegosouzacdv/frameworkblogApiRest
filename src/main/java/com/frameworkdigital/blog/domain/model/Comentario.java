package com.frameworkdigital.blog.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "COMENTARIO")
public class Comentario {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COM_CODIGO",unique=true, nullable=false)
	private Long id;
	
	
	@Column(name = "COM_DATACADASTRO")
	@DateTimeFormat(pattern="dd/MM/yyyy hh:MM:ss")
	private LocalDateTime dataHoraPublicacao;
	
	
	@Column(name="CAT_DESCRICAO")
	private String descricao;
	
	
	@JoinColumn(name = "USU_CODIGO_COMENTARIO", referencedColumnName = "USU_CODIGO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@JoinColumn(name = "POS_CODIGO", referencedColumnName = "POS_CODIGO")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Post post;
	
	



	public Comentario(Long id, LocalDateTime dataHoraPublicacao, String descricao, Usuario usuario, Post post) {
		super();
		this.id = id;
		this.dataHoraPublicacao = dataHoraPublicacao;
		this.descricao = descricao;
		this.usuario = usuario;
		this.post = post;
		
	}

	
}
