package com.daily.menu.springboot.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daily.menu.springboot.models.apirest.models.service.IUsuarioService;
import com.daily.menu.springboot.models.apirest.models.service.UploadFileServiceImpl;
import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Usuario;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	@Autowired
	// hace referencia a otro service
	private UploadFileServiceImpl uploadService;
	
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
	 * LISTAR PAGINACION
	 */
	@GetMapping("/usuario/page/{page}")
	public Page<Usuario> index(@PathVariable Integer page) {
		return usuarioService.findall(PageRequest.of(page, 4));
	}
	
	
	/**
	 * LISTAR POR ID
	 */

	@GetMapping("/usuario/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {

			usuario = usuarioService.finById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response.put("mensaje", "el registro ID: ".concat(id.toString().concat(" no Existe en la Base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
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
			e.printStackTrace();
			response.put("mensaje", "Error al registrar nuevo usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con èxito!");
		response.put("registro", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	
	/**
	 * ACTUALIZAR
	 */
	@PutMapping("/usuario/{id}")
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, @PathVariable Long id,
			BindingResult result) {

		Usuario usuarioActual = usuarioService.finById(id);
		Usuario usuarioUpdated = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		if (usuarioActual == null) {
			response.put("mensaje", "error : no se pudo editar el usuario ID: "
					.concat(id.toString().concat(" no Existe en la Base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			String clave = usuario.getPassword();
			String passwordBcrypt = passwordEncoder.encode(clave);
			usuario.setPassword(passwordBcrypt);
			
			usuarioActual.setPassword(usuario.getPassword());
			usuarioUpdated = usuarioService.save(usuarioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar  el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con èxito!");
		response.put("registro", usuarioUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	/**
	 * ACTUALIZAR
	 */
	@PutMapping("/usuario/completar/{id}")
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> completar(@Valid @RequestBody Usuario usuario, @PathVariable Long id,
			BindingResult result) {

		Usuario usuarioActual = usuarioService.finById(id);
		Usuario usuarioUpdated = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		if (usuarioActual == null) {
			response.put("mensaje", "error : no se pudo editar el usuario ID: "
					.concat(id.toString().concat(" no Existe en la Base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
		
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setRoles(usuario.getRoles());
			usuarioActual.setTipo_documento(usuario.getTipo_documento());
			usuarioActual.setOcupacion(usuario.getOcupacion());
			usuarioActual.setNombreEmpresa(usuario.getNombreEmpresa());
			usuarioActual.setTelefono(usuario.getTelefono());
			usuarioActual.setFoto(usuario.getFoto());
			usuarioActual.setDireccion(usuario.getDireccion());
			usuarioActual.setCuidad(usuario.getCuidad());
			usuarioActual.setEstado(usuario.getEstado());
			usuarioActual.setCodigoPostal(usuario.getCodigoPostal());
			usuarioActual.setLinkedink(usuario.getLinkedink());
			usuarioActual.setFacebook(usuario.getFacebook()); 
			usuarioActual.setTwittter(usuario.getTwittter());
			usuarioActual.setInstagram(usuario.getInstagram());
			usuarioActual.setCodigoPostal(usuario.getCodigoPostal());
		usuarioActual.setPassword(usuario.getPassword());
			usuarioUpdated = usuarioService.save(usuarioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar  el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con èxito!");
		response.put("registro", usuarioUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	/**
	 * ELIMINAR
	 */

	@DeleteMapping("/usuario/{id}")
	@Secured("ROLE_ADMIN")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Usuario usuario = usuarioService.finById(id);

			String nombreFotoAnterior = usuario.getFoto();

			  uploadService.eliminar(nombreFotoAnterior);

			usuarioService.findByDelete(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar  el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "el usuario fue eliminado con èxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	/**
	 * CARGAR FOTO se complemnta con dos lineas en aplication.properties
	 */
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/usuario/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Usuario usuario = usuarioService.finById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = usuario.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			usuario.setFoto(nombreArchivo);

			usuarioService.save(usuario);
			response.put("registro", usuario);
			response.put("mensaje", "haz subido correctamente la imagen: " + nombreArchivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * CARGAR FOTO para mostrar
	 */

	@GetMapping("uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}


}
