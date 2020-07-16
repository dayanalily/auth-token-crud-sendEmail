package com.daily.menu.springboot.models.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.daily.menu.springboot.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	/*
	 * este metodo es para busqueda para agregar mas filtros usar la palabra And
	 * ejemplo findByUsernameAndEmail
	 **/
	
	public Usuario findByUsername(String username);
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);
	
	
}
