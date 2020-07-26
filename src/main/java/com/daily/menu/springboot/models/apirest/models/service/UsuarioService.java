package com.daily.menu.springboot.models.apirest.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daily.menu.springboot.models.apirest.models.dao.IUsuarioDao;
import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private IUsuarioDao usuarioDao;

	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByEmail(email);


		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario encontrado '" + email + "' en el sistema!");
			throw new UsernameNotFoundException(
					"Error en el login: no existe el usuario '" + email + "' en el sistema!");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role:  " + authority.getAuthority())).collect(Collectors.toList());

		// despues de password usuario.getEnabled(), revisar curso
		return new User(usuario.getEmail(), usuario.getPassword() ,true, true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String email) {
		return usuarioDao.findByEmail(email);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findall() {
	
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario finById(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findall(Pageable pegeable) {
		// TODO Auto-generated method stub
		return  usuarioDao.findAll(pegeable);
	}

	@Override
	@Transactional(readOnly = true)
	public void findByDelete(Long id) {
		// TODO Auto-generated method stub
		return;
	}



	

	

	


	
	
	 

	

}
