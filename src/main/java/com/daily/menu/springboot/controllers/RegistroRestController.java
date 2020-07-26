package com.daily.menu.springboot.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daily.menu.springboot.models.apirest.models.service.IregistroServiceImp;
import com.daily.menu.springboot.models.apirest.models.service.UploadFileServiceImpl;
import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Region;
import com.daily.menu.springboot.models.entity.registro;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class RegistroRestController {
	@Autowired
	private IregistroServiceImp registroService;

	@Autowired
	// hace referencia a otro service
	private UploadFileServiceImpl uploadService;

	// private final Logger log = LoggerFactory.getLogger(RegistroRestController.class);

	/**
	 * LISTAR
	 */
	@GetMapping("/registro")
	public List<registro> index() {
		return registroService.findall();
	}

	/**
	 * LISTAR PAGINACION
	 */
	@GetMapping("/registro/page/{page}")
	public Page<registro> index(@PathVariable Integer page) {
		return registroService.findall(PageRequest.of(page, 4));
	}

	/**
	 * LISTAR POR ID
	 */
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/registro/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {

		registro registro = null;
		Map<String, Object> response = new HashMap<>();
		try {

			registro = registroService.finById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (registro == null) {
			response.put("mensaje", "el registro ID: ".concat(id.toString().concat(" no Existe en la Base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<registro>(registro, HttpStatus.OK);
	}

	/**
	 * CREAR
	 */
	// @RequestBody para recibir un json
	@PostMapping("/registro")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> create(@Valid @RequestBody registro registro, BindingResult result) {
		// registro.setCreateAt(new Date());
		// return registroService.save(registro);

		registro registroNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		try {
			registroNew = registroService.save(registro);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al registrar nuevo usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con èxito!");
		response.put("registro", registroNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * ACTUALIZAR
	 */
	@PutMapping("/registro/{id}")
	@Secured("ROLE_ADMIN")
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody registro registro, @PathVariable Long id,
			BindingResult result) {

		registro registroActual = registroService.finById(id);
		registro registroUpdated = null;

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		if (registroActual == null) {
			response.put("mensaje", "error : no se pudo editar el usuario ID: "
					.concat(id.toString().concat(" no Existe en la Base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			registroActual.setNombre(registro.getNombre());
			registroActual.setApellido(registro.getApellido());
			registroActual.setEmail(registro.getEmail());
			registroActual.setRegion(registro.getRegion());
			registroActual.setPais(registro.getPais());
			registroUpdated = registroService.save(registroActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar  el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con èxito!");
		response.put("registro", registroUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * ELIMINAR
	 */

	@DeleteMapping("/registro/{id}")
	@Secured("ROLE_ADMIN")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			registro registro = registroService.finById(id);

			String nombreFotoAnterior = registro.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			registroService.delete(id);

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
	/*@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/registro/upload")
	 public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		registro registro = registroService.finById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = registro.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			registro.setFoto(nombreArchivo);

			registroService.save(registro);
			response.put("registro", registro);
			response.put("mensaje", "haz subido correctamente la imagen: " + nombreArchivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	} 
	*/

	/**
	 * CARGAR FOTO para mostrar
	 */

/*	@GetMapping("uploads/img/{nombreFoto:.+}")
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
*/
	
	/**
	 * LISTAR regiones
	 */
	@GetMapping("/registro/regiones")
	@Secured("ROLE_ADMIN")
	public List<Region> listarRegiones() {
		return registroService.findAllRegiones();
	}
	
	/**
	 * LISTAR PAISES
	 */
	@GetMapping("/registro/pais")
	//@Secured("ROLE_ADMIN")
	public List<Pais> listarPais() {
		return registroService.findAllPais();
	}


}
