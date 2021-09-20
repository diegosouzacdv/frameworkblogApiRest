package com.frameworkdigital.blog.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.frameworkdigital.blog.domain.filter.PostFilter;
import com.frameworkdigital.blog.domain.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	 @Query("select post from Post post " 
			 + " 	left join Usuario usu on usu = post.usuario"
			 + " 	left join Categoria cat on cat = post.categoria"
			 + " where 1=1 "
            + "and  ("
            + ":#{#filter?.parametro} is null or usu.nome like %:#{#filter?.parametro}% "
            + "or (:#{#filter?.parametro} is null or cat.nome like %:#{#filter?.parametro}% ) "
            + "or (:#{#filter?.parametro} is null or post.titulo like %:#{#filter?.parametro}% ) "
            + "or (:#{#filter?.parametro} is null or post.descricao like %:#{#filter?.parametro}% ) "
            + " ) order by post.dataHoraPublicacao asc  " 
            )
	public List<Post> findByFilter(PostFilter filter,Pageable pageable);
	

}
