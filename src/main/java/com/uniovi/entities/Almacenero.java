package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

public class Almacenero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String password;
	@Transient
	private String passwordConfirm;
	private String role;
	

	@OneToMany(mappedBy="almacenero", cascade = CascadeType.ALL)
	private Set<Pedido> pedidos = new HashSet<Pedido>();
	
	Almacenero(){}

	public Almacenero(String email, String name, String password, String passwordConfirm, String role) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public Set<Pedido> getPedidos() {
		return pedidos;
	}

	



}
