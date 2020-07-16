package com.daily.menu.springboot.models.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Region;
import com.daily.menu.springboot.models.entity.Usuario;
import com.daily.menu.springboot.models.entity.registro;



public interface IregistroServiceImp {
	
	public List<registro> findall();
	public Page<registro> findall(Pageable pegeable);
	public registro finById(Long id);
	public registro save(registro registro);
	public void  delete(Long id);
	public List<Region> findAllRegiones();
	public List<Pais> findAllPais();


}
