package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {

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

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<ProductosCarrito> productosCarrito = new HashSet<ProductosCarrito>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Pedido> pedidos = new HashSet<Pedido>();

	@OneToMany(mappedBy = "almacenero", cascade = CascadeType.ALL)
	private Set<OrdenTrabajo> ordenesTrabajo = new HashSet<OrdenTrabajo>();

	public User() {
	}

	public User(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
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

	public Long getId() {
		return id;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	Set<ProductosCarrito> _getProductosCarrito() {
		return productosCarrito;
	}

	public Set<ProductosCarrito> getProductosCarrito() {
		return new HashSet<ProductosCarrito>(productosCarrito);
	}

	Set<OrdenTrabajo> _getOrdenesTrabajo() {
		return ordenesTrabajo;
	}

	public Set<OrdenTrabajo> getOrdenesTrabajo() {
		return new HashSet<OrdenTrabajo>(ordenesTrabajo);
	}

	public void addOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenesTrabajo.add(ordenTrabajo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + "]";
	}
}