package com.daily.menu.springboot.models.apirest.models.service;

import java.util.List;

import com.daily.menu.springboot.models.entity.Usuario;
import com.daily.menu.springboot.models.entity.registro;

public interface IUsuarioService {
	
	public Usuario findByUsername(String email);
	public List<Usuario> findall();
	public Usuario save(Usuario usuario);

}
