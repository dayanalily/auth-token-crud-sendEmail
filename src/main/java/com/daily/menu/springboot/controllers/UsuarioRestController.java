package com.daily.menu.springboot.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daily.menu.springboot.models.apirest.models.service.IUsuarioService;
import com.daily.menu.springboot.models.entity.Usuario;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * LISTAR
	 */
	@GetMapping("/usuario/listar")
	public List<Usuario> index() {
		return usuarioService.findall();
	}

	/**
	 * CREAR
	 */
	// @RequestBody para recibir un json
	@PostMapping("/usuario/registrar")

	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		// registro.setCreateAt(new Date());
		// return registroService.save(registro);

		Usuario usuarioNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		try {
			String clave = usuario.getPassword();
			String passwordBcrypt = passwordEncoder.encode(clave);
			usuario.setPassword(passwordBcrypt);
			usuarioNew = usuarioService.save(usuario);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al registrar nuevo usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con èxito!");
		response.put("registro", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
