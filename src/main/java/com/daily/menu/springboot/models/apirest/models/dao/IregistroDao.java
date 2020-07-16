package com.daily.menu.springboot.models.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Region;
import com.daily.menu.springboot.models.entity.registro;

public interface IregistroDao extends JpaRepository<registro, Long> {

	@Query("from Region") // para persornalizar las consultas por tabla
	
	public List<Region> findAllRegiones();
	
	@Query("from Pais") // para persornalizar las consultas por tabla
	
	public List<Pais> findAllPais();
	/*
	 * este metodo es para busqueda para agregar mas filtros usar la palabra And
	 * ejemplo findByUsernameAndEmail
	 **/
	

}
