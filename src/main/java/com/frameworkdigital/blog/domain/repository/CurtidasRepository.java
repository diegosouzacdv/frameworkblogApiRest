package com.frameworkdigital.blog.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.frameworkdigital.blog.domain.model.Curtidas;

@Repository
public interface CurtidasRepository extends JpaRepository<Curtidas, Long> {

	public List<Curtidas>  findByPostIdAndUsuarioId(Long id, Long id2);

}
