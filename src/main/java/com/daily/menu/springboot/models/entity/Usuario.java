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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	  @SequenceGenerator(sequenceName = "seq_usuario", allocationSize = 1, name = "usuario_seq")
	private long id;

	
	@NotEmpty(message = "No puede estar vacio")
	@Email(message = "No es una direccion de correo valida")
	@Column(nullable = false, unique = true) // nullable = no guardar vacio , unique = no se debe repetir

	private String email;
	@Column(length = 60)
	private String password;
	private String enabled;

	private String nombre;
	private String apellido;
	private Long tipo_documento;
	private Long numero_documento;
	private Boolean terminos;
	private String foto;
	
	private String Pais;
	private String ocupacion;
	private String nombreEmpresa;
	private String telefono;
	private String direccion;
	private String cuidad;
	private String estado;
	private String codigoPostal;
	private String linkedink;
	private String  facebook;
	private String  instagram;
	private String  twittter;
	
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
	
	//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ManyToMany(cascade = CascadeType.MERGE,fetch= FetchType.LAZY)
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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



	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupasion) {
		this.ocupacion = ocupasion;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCuidad() {
		return cuidad;
	}

	public void setCuidad(String cuidad) {
		this.cuidad = cuidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLinkedink() {
		return linkedink;
	}

	public void setLinkedink(String linkedink) {
		this.linkedink = linkedink;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTwittter() {
		return twittter;
	}

	public void setTwittter(String twittter) {
		this.twittter = twittter;
	}



	public String getPais() {
		return Pais;
	}

	public void setPais(String pais) {
		Pais = pais;
	}



	private static final long serialVersionUID = 1L;

}
