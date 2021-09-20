package com.frameworkdigital.blog.domain.model;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "POST")
public class Post {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="POS_CODIGO",unique=true, nullable=false)
	private Long id;
	
	@Column(name = "POS_DATACADASTRO")
	@DateTimeFormat(pattern="dd/MM/yyyy hh:MM:ss")
	private LocalDateTime dataHoraPublicacao;
	
	
	@Column(name="POS_TITULO",nullable = false,length = 500)
	private String titulo;
	
	
	@Column(name="POS_DESCRICAO")
	private String descricao;
	
	@Column(name="POS_VISUALIZACOES")
	private int visualizacoes;
	
	@Column(name="POS_TIPO_POSTAGEM")
	private int tipoPostagem;
	
	
	@JoinColumn(name = "CAT_CODIGO", referencedColumnName = "CAT_CODIGO")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Categoria categoria;
	

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Comentario> comentarios;
	
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Curtidas> curtidas;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<ImagensPost> imagensPost;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Link> links;
	
	@JoinColumn(name = "USU_CODIGO_POST", referencedColumnName = "USU_CODIGO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Usuario usuario;

	@Transient
	private int totalCurtidas;
	
	@Transient
	private int totalComentarios;
	
	public int getTotalCurtidas() {
		return curtidas==null?0:curtidas.size();
	}
	
	public int getTotalComentarios() {
		return comentarios==null?0: comentarios.size();
	}
	
}
