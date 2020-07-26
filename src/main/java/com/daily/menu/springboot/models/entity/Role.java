package com.daily.menu.springboot.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
     @SequenceGenerator(sequenceName = "seq_roles", allocationSize = 1, name = "roles_seq")
	private long id;

	@Column(unique = true, length = 20)
	private String nombre;

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private static final long serialVersionUID = 1L;

}
