package com.frameworkdigital.blog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frameworkdigital.blog.domain.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{

}
