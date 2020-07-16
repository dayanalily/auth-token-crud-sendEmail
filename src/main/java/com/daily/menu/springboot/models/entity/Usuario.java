package com.daily.menu.springboot.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	
	@NotEmpty(message = "No puede estar vacio")
	@Email(message = "No es una direccion de correo valida")
	@Column(nullable = false, unique = true) // nullable = no guardar vacio , unique = no se debe repetir

	private String username;
	@Column(length = 60)
	private String password;
	private String enabled;

	private String nombre;
	private String apellido;
	private Long tipo_documento;
	private Long numero_documento;
	private Boolean terminos;
	private String foto;
	//@Column(unique = true)
	//private String email;

	/*
	 * 
	 * con esta anotacion @ManyToOne se crea la relaciones de muchos a muchos o a
	 * uno el tipo cascada es para eliminar la relacion cuando sea eliminado quien
	 * tenga relacion
	 * 
	 * 
	 */

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// para asociar con role
	@JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "usuario_id", "role_id" }) })
	private List<Role> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public Long getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(Long tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	
	
	public Long getNumero_documento() {
		return numero_documento;
	}

	public void setNumero_documento(Long numero_documento) {
		this.numero_documento = numero_documento;
	}

	 public Boolean getTerminos() {
	return terminos;
		}

	public void setTerminos(Boolean terminos) {
		this.terminos = terminos;
	 }

 public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}



	private static final long serialVersionUID = 1L;

}
