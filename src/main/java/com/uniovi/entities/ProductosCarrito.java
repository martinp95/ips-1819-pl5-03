package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.types.ProductosCarritoKey;

@Entity
@IdClass(ProductosCarritoKey.class)
public class ProductosCarrito {

	@Id
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCTO_ID")
	private Producto producto;

	private int cantidad;
	private double precioProductoCantidad;

	public ProductosCarrito() {
	}

	public ProductosCarrito(User user, Producto producto, int cantidad) {
		this.user = user;
		this.producto = producto;
		this.setCantidad(cantidad);
		calcularPrecioProductoCantidad();
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void calcularPrecioProductoCantidad() {
		this.precioProductoCantidad = producto.getPrecio() * cantidad;
	}	

	public double getPrecioProductoCantidad() {
		return precioProductoCantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		ProductosCarrito other = (ProductosCarrito) obj;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}