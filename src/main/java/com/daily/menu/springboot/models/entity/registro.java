package com.daily.menu.springboot.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;;

@Entity
@Table(name = "registros")
public class registro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 4, max = 15, message = "El tamaño tiene que estar entre 4 y 15") // Cantidad de caracteres
	@Column(nullable = false) // con esta linea se define como la linea de abajo no debe ser null;

	private String nombre;
	@NotEmpty(message = "No puede estar vacio")
	private String apellido;
	private Long tipo_documento;
	private Long numero_documento;

	@NotEmpty(message = "No puede estar vacio")
	@Email(message = "No es una direccion de correo valida")
	@Column(nullable = false, unique = true) // nullable = no guardar vacio , unique = no se debe repetir
	private String email;
	private Long pass;
	private Boolean terminos;
	private String foto;

	@NotNull(message ="la region no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	// esto se usa para ignorar los valores hibernateLazyInitializer - handler por usar FetchType.LAZY 
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Region region;
	
	@NotNull(message ="el campo pais no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pais_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Pais pais;

//	@Column(name="create_at")
//	@Temporal(TemporalType.DATE)
//	private Date createAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPass() {
		return pass;
	}

	public void setPass(Long pass) {
		this.pass = pass;
	}

	public Boolean getTerminos() {
		return terminos;
	}

	public void setTerminos(Boolean terminos) {
		this.terminos = terminos;
	}

//	public Date getCreateAt() {
//		return createAt;
//	}
//
//	public void setCreateAt(Date createAt) {
//		this.createAt = createAt;
//	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	
	
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}



	private static final long serialVersionUID = 1L;

}
