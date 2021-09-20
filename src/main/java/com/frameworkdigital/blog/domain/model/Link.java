package com.frameworkdigital.blog.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "LINK")
public class Link {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LIN_CODIGO",unique=true, nullable=false)
	private Long id;
	
	
	@Column(name="LIN_DESCRICAO",nullable = false)
	private String descricao;
	
	@JoinColumn(name = "POS_CODIGO", referencedColumnName = "POS_CODIGO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Post post;

	@Override
	public String toString() {
		return "Link [" + (id != null ? "id=" + id + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "") + (post != null ? "post=" + post : "")
				+ "]";
	}

	public Link(Long id, String descricao, Post post) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.post = post;
	}
	
	
	
}
