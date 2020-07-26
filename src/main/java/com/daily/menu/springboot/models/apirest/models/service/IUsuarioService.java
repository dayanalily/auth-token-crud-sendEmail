package com.daily.menu.springboot.models.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String email);
	public List<Usuario> findall();
    public Page<Usuario> findall(Pageable pegeable);
	public Usuario save(Usuario usuario);
	public Usuario finById(Long id);
//	public List<Pais> findAllPais();
	public void findByDelete(Long id);

}
