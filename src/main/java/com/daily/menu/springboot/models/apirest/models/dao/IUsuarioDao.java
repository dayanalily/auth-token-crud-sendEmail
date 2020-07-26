package com.daily.menu.springboot.models.apirest.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	/*
	 * este metodo es para busqueda para agregar mas filtros usar la palabra And
	 * ejemplo findByUsernameAndEmail
	 **/
	
	public Usuario findByEmail(String email);
	@Query("select u from Usuario u where u.email=?1")
	public Usuario findByEmail2(String email);
	public Page<Usuario> findAll(Pageable pegeable);
	//public List<Pais> findAllPais();


	
	
}
